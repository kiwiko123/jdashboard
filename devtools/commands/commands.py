import pathlib
import subprocess

class ExecutableCommand:
    def __init__(self, command_args: [str], **popen_kwargs) -> None:
        self._command_args = command_args
        self._popen_kwargs = popen_kwargs
        self._process = None

    def is_running(self) -> bool:
        return self._process is not None

    def start(self) -> subprocess.Popen:
        self._process = subprocess.Popen(self._command_args, **self._popen_kwargs)
        return self._process

    def get(self) -> subprocess.Popen:
        return self._process




def run_react_web_server() -> subprocess.Popen:
    command_args = ['npm', 'start']
    working_directory = pathlib.Path('../../react')
    return subprocess.Popen(command_args, cwd=working_directory)


if __name__ == '__main__':
    run_react_command = run_react_web_server()
    print(run_react_command)