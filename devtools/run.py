import contextlib
import pathlib
import signal
import subprocess
import time
from files.sourceactions import SourceWatcher

SUPPORTED_FILE_EXTENSIONS = {'.java', '.sql', '.jsx', '.js', '.css'}


def stop_process(process: subprocess.Popen) -> None:
    process.send_signal(signal.SIGINT)

def run(command_args: [str], **run_kwargs) -> subprocess.CompletedProcess:
    result = subprocess.run(command_args, **run_kwargs)
    if result.returncode != 0:
        raise RuntimeError('Failed to run command "{0}" with return code {1}'.format(command_args, result.returncode))
    return result

def start_database() -> subprocess.CompletedProcess:
    command_args = ['pg_ctl', '-D', '/usr/local/var/postgres', 'start']
    return run(command_args)

def stop_database() -> subprocess.CompletedProcess:
    command_args = ['pg_ctl', '-D', '/usr/local/var/postgres', 'stop']
    return run(command_args)

@contextlib.contextmanager
def run_database() -> subprocess.CompletedProcess:
    command = start_database()
    try:
        yield command
    finally:
        stop_database()

def start_react_web_server() -> subprocess.Popen:
    command_args = ['npm', 'start']
    current_working_directory = '../react'
    return subprocess.Popen(command_args, cwd=current_working_directory)

@contextlib.contextmanager
def run_react_web_server() -> subprocess.Popen:
    react_web_server_process = start_react_web_server()
    try:
        yield react_web_server_process
    finally:
        stop_process(react_web_server_process)


class WebServerSourceWatcher(SourceWatcher):

    def __init__(self, root_directory: pathlib.Path):
        super().__init__(root_directory, file_extensions=SUPPORTED_FILE_EXTENSIONS)
        self._react_web_server_process = None
        self.refresh_seconds = 10

    def run(self) -> None:
        try:
            with run_database():
                self._react_web_server_process = start_react_web_server()
                self.monitor()
        finally:
            self.stop_web_server()

    def stop_web_server(self) -> None:
        if self._react_web_server_process is not None:
            stop_process(self._react_web_server_process)

    def restart_web_server(self) -> None:
        self.stop_web_server()
        if self._react_web_server_process.returncode is not None:
            self._react_web_server_process = start_react_web_server()

    def on_change(self) -> None:
        self.restart_web_server()


if __name__ == '__main__':
    source_code_root = pathlib.Path('../')
    watcher = WebServerSourceWatcher(source_code_root)
    watcher.run()