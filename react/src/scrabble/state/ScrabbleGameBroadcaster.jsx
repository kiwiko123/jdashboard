import { get, merge } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import { createAction } from '../../state/actions';
import { RequestService, extractResponse, handleErrors } from '../../common/js/requests';
import {
    NEW_GAME_URL,
    VALIDATE_MOVE_URL,
    PLAY_MOVE_URL,
} from '../js/urls';

const SERVER_URL = 'http://localhost:8080';

function getDefaultState() {
    return {
        board: {
            board: [],
            rowCount: 0,
            columnCount: 0,
            isGameOver: false,
        },
        player: {
            id: null,
            availableTiles: [],
            playedTiles: [],
            submittedTiles: [],
        },
        opponent: {
            id: null,
            availableTiles: [],
            playedTiles: [],
        },
        canSubmit: false,
    };
}

export default class ScrabbleGameBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.updateGameState = this.updateGameState.bind(this);

        this.setState({
            ...getDefaultState(),
            actions: {
                newGame: createAction(this.newGame.bind(this)),
                dropTileOnBoard: createAction(this.dropTileOnBoard.bind(this)),
                onPlayerDropTileOutOfBoard: createAction(this.onPlayerDropTileOutOfBoard.bind(this)),
                recallTiles: createAction(this.recallTiles.bind(this)),
                submitTiles: createAction(this.submitTiles.bind(this)),
            },
        });

        this.requestService = new RequestService(SERVER_URL)
            .withResponseExtractor(extractResponse)
            .withErrorHandler(response => handleErrors(response, errors => this.setState({ errors })));

        this.state.actions.newGame();
    }

    updateGameState(payload) {
        this.requestService.setPersistentPayload({ gameId: payload.id });
        this.setState({
            board: get(payload, 'board', []),
            player: get(payload, 'player', {}),
            opponent: get(payload, 'opponent', {}),
        });
    }

    newGame() {
        this.requestService.get(NEW_GAME_URL)
            .then(this.updateGameState);
        this.setState({
            canSubmit: false,
        });
    }

    submitTiles() {
        const payload = {
            player: this.state.player,
        };
        this.requestService.post(PLAY_MOVE_URL, payload);
        // TODO update game state?
    }

    dropTileOnBoard(event, rowIndex, columnIndex) {
        event.stopPropagation();
        const payload = event.dataTransfer.getData('text/plain');
        const tile = JSON.parse(payload);
        const submittedTile = {
            ...tile,
            row: rowIndex,
            column: columnIndex,
        };

        const { board, player } = this.state;
        const submittedTiles = [...player.submittedTiles, submittedTile];
        const matrix = [...board.board];
        matrix[rowIndex][columnIndex] = tile;

        this.setState({
            board: {
                ...board,
                board: matrix,
            },
            player: {
                ...player,
                // add the newly-dropped tile into the player's submitted tiles
                submittedTiles,
                // remove the newly-dropped tile from the player's available tiles
                availableTiles: player.availableTiles.filter(t => t.id !== tile.id),
            },
            canSubmit: submittedTiles.length > 0,
        });

        this.validatePlayerTiles(submittedTiles);
    }

    validatePlayerTiles(tiles) {
        const payload = { tiles };
        this.requestService.post(VALIDATE_MOVE_URL, payload)
            .then(payload => this.setState({ invalidSubmittedTiles: payload }));
        // TODO highlight invalid tiles in red
    }

    onPlayerDropTileOutOfBoard(event) {
        const payload = event.dataTransfer.getData('text/plain');
        const tile = JSON.parse(payload);
        const player = { ...this.state.player };
        merge(player, {
            availableTiles: [
                ...player.availableTiles,
                tile,
            ],
            submittedTiles: player.submittedTiles.filter(t => t.id !== tile.id),
        });
        this.setState({ player });
        this.removeTilesFromBoard([tile]);
    }

    recallTiles() {
        const { player } = this.state;
        this.removeTilesFromBoard(player.submittedTiles);
        this.setState({
            player: {
                ...player,
                availableTiles: [
                    ...player.availableTiles,
                    ...player.submittedTiles,
                ],
                submittedTiles: [],
            },
            errors: [],
        });
    }

    removeTilesFromBoard(tiles) {
        const { board } = this.state;
        const matrix = [...board.board];
        tiles.forEach((tile) => { matrix[tile.row][tile.column] = null; });

        this.setState({
            board: {
                ...board,
                board: matrix,
            },
        });
    }
}