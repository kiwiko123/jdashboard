import { get, merge, set } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import { RequestService, extractResponse } from '../../common/js/requests';
import { getUrlParameters } from '../../common/js/urltools';
import {
    NEW_GAME_URL,
    LOAD_GAME_URL,
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
            invalidSubmittedTiles: [],
        },
        opponent: {
            id: null,
            availableTiles: [],
            playedTiles: [],
        },
        canSubmit: false,
        errors: [],
    };
}

export default class ScrabbleGameBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.updateGameState = this.updateGameState.bind(this);

        this.setState({
            ...getDefaultState(),
            actions: {
                newGame: this.newGame.bind(this),
                dropTileOnBoard: this.dropTileOnBoard.bind(this),
                onPlayerDropTileOutOfBoard: this.onPlayerDropTileOutOfBoard.bind(this),
                recallTiles: this.recallTiles.bind(this),
                submitTiles: this.submitTiles.bind(this),
            },
        });

        this.requestService = new RequestService(SERVER_URL)
            .withResponseExtractor(extractResponse);

        this.state.actions.newGame();
    }

    updateGameState(payload) {
        this.requestService.setPersistentPayload({ gameId: payload.id });
        const { board, player, opponent } = this.state;
        merge(board, get(payload, 'board', []));
        merge(player, get(payload, 'player', {}));
        merge(opponent, get(payload, 'opponent', {}));
        this.setState({
            board,
            player,
            opponent,
        });

        // TODO testing out merge
//         this.setState({
//             board: get(payload, 'board', []),
//             player: get(payload, 'player', {}),
//             opponent: get(payload, 'opponent', {}),
//         });
    }

    newGame() {
        let url = NEW_GAME_URL;
        const urlParameters = getUrlParameters();
        const gameId = get(urlParameters, 'gameId');
        if (gameId) {
            url = `${LOAD_GAME_URL}/${gameId}`;
        }

        this.requestService.get(url)
            .then(this.updateGameState)
            .catch(error => this.setState({ errors: ['There was an error communicating with the server.'] }));
        this.setState({
            canSubmit: false,
        });
    }

    submitTiles() {
        const payload = {
            tiles: get(this.state, 'player.submittedTiles', []),
        };
        this.requestService.post(PLAY_MOVE_URL, payload)
            .then(this.updateGameState)
            .catch(error => this.setState({ errors: ['There was an error submitting the tiles']}));
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
            .then((payload) => {
                const { player } = this.state;
                const invalidSubmittedTiles = payload || [];
                set(player, 'invalidSubmittedTiles', invalidSubmittedTiles);
                this.setState({
                    player,
                    canSubmit: invalidSubmittedTiles.length === 0,
                });
            });
        // TODO highlight invalid tiles in red
    }

    onPlayerDropTileOutOfBoard(event) {
        const payload = event.dataTransfer.getData('text/plain');
        const tile = JSON.parse(payload);
        if (!(tile.row && tile.column)) {
            // The tile wasn't actually ever dropped on the board.
            return;
        }

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