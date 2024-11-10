import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MinesweeperBoard {

    private List<String> _boardRows;

    private Supplier<Integer> _boardWidth = () -> {
        var width = _boardRows.stream().findAny().map(row -> row.length()).orElse(0);
        _boardWidth = () -> width;
        return width;
    };

    private record Position(int rowIndex, int columnIndex) {
    };

    private List<Position> adjacentCells(int rowIndex, int columnIndex) {
        return List.of(
                new Position(rowIndex - 1, columnIndex - 1),
                new Position(rowIndex - 1, columnIndex),
                new Position(rowIndex - 1, columnIndex + 1),
                new Position(rowIndex, columnIndex - 1),
                new Position(rowIndex, columnIndex + 1),
                new Position(rowIndex + 1, columnIndex - 1),
                new Position(rowIndex + 1, columnIndex),
                new Position(rowIndex + 1, columnIndex + 1));
    }

    MinesweeperBoard(List<String> boardRows) {
        _boardRows = boardRows;
    }

    List<String> withNumbers() {
        return IntStream
                .range(0, _boardRows.size())
                .mapToObj(row -> annotateRow(row))
                .collect(Collectors.toList());
    }

    private String annotateRow(int rowIndex) {
        String row = _boardRows.get(rowIndex);
        return IntStream
                .range(0, row.length())
                .mapToObj(columnIndex -> annotateCell(rowIndex, columnIndex))
                .collect(Collectors.joining());
    }

    private CharSequence annotateCell(int rowIndex, int columnIndex) {

        if (_boardRows.get(rowIndex).charAt(columnIndex) == '*')
            return "*";

        var cnt = 0;
        for (Position position : adjacentCells(rowIndex, columnIndex)) {
            if (position.rowIndex < 0 || position.rowIndex >= _boardRows.size())
                continue;

            if (position.columnIndex < 0 || position.columnIndex >= _boardWidth.get())
                continue;
            
            if (_boardRows.get(position.rowIndex).charAt(position.columnIndex) == '*')
                cnt++;
        }

        return cnt == 0 ? " " : String.valueOf(cnt);
    }

}