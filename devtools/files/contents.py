import pathlib

def _get_file_content(path: pathlib.Path) -> str:
    with path.open() as infile:
        return infile.read()


class FileSnapshot:

    def __init__(self, path: pathlib.Path, should_load=False) -> None:
        self._path = path
        self._bytes_size = path.stat().st_size
        self._content = _get_file_content(path) if should_load else None

    @property
    def path(self) -> pathlib.Path:
        return self._path

    @property
    def size(self) -> int:
        return self._bytes_size

    @property
    def content(self) -> str:
        return self._content

    def reload(self) -> None:
        self._content = _get_file_content(self._path)

    def __eq__(self, other) -> bool:
        if other is self:
            return True
        if not isinstance(other, FileSnapshot):
            return False
        return self._path == other._path and self._bytes_size == other._bytes_size and self._content == other._content

    def __hash__(self) -> int:
        return hash((self._path, self._bytes_size, self._content))


if __name__ == '__main__':
    pass