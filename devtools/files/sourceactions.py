import pathlib
import time
from .contents import FileSnapshot

SUPPORTED_FILE_EXTENSIONS = {'java', 'jsx', 'js', 'css'}

def _make_extensions(extensions: {str}) -> {str}:
    result = set()
    for extension in extensions:
        if not extension.startswith('.'):
            extension = '.{0}'.format(extension)
        result.add(extension)
    return result

class SourceReader:

    def __init__(self, root_directory: pathlib.Path, file_extensions=None) -> None:
        self._root = root_directory
        self._supported_file_extensions = None if file_extensions is None else _make_extensions(file_extensions)

    def gather(self) -> {pathlib.Path}:
        return self.gather_from(self._root)

    def gather_from(self, root: pathlib.Path) -> {pathlib.Path}:
        result = set()
        for path in root.iterdir():
            if path.is_dir():
                subfiles = self.gather_from(path)
                result.update(subfiles)
            elif self._supported_file_extensions is None or path.suffix in self._supported_file_extensions:
                result.add(path)

        return result



class SourceWatcher:

    def __init__(self, root_directory: pathlib.Path, file_extensions=None):
        self._source_reader = SourceReader(root_directory, file_extensions)
        self._all_files = {path: FileSnapshot(path) for path in self._source_reader.gather()}
        self.refresh_seconds = 60

    def monitor(self) -> None:
        while self.should_update():
            if self.has_changed():
                self.on_change()
            time.sleep(self.refresh_seconds)

    def has_changed(self) -> bool:
        fresh_snapshots = self.get_fresh_snapshots()
        result = False
        if len(fresh_snapshots) == len(self._all_files):
            result = fresh_snapshots != self._all_files
        else:
            result = True

        self._all_files = fresh_snapshots
        return result

    def get_fresh_snapshots(self) -> {pathlib.Path: FileSnapshot}:
        return {path: FileSnapshot(path) for path in self._source_reader.gather()}

    def should_update(self) -> bool:
        return True

    def on_change(self) -> None:
        pass



if __name__ == '__main__':
    pass