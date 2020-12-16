import { get, merge, set } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import Request from '../../common/js/Request';
import { getUrlParameters, updateQueryParameters } from '../../common/js/urltools';
import {
    NEW_GAME_URL,
    LOAD_GAME_URL,
    VALIDATE_MOVE_URL,
    PLAY_MOVE_URL,
    SAVE_GAME_URL,
} from '../js/urls';
import DashboardAlertActions from '../../dashboard/state/actions/DashboardAlertActions';

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
        isLoaded: false,
    };
}

function alertErrorHandler(response) {
    const errorMessages = get(response, 'errors', []);
    errorMessages.forEach(message => DashboardAlertActions.addAlert({
        message,
        bannerType: 'danger',
        autoDismissMillis: 5000,
    }));
}

export default class ScrabbleGameBroadcaster extends Broadcaster {

    constructor() {
        super();

        this.reRenderMillis = 500;
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
    }

    receive(state, broadcasterId) {
        if (broadcasterId === 'UserDataBroadcaster') {
            this.setState({ userId: state.userId });
            if (state.isLoaded && !this.state.gameId) {
                this.startGame();
            }
        }
    }

    updateGameState(payload) {
        const { board, player, opponent } = this.state;
        merge(board, get(payload, 'board', []));
        merge(player, get(payload, 'player', {}));
        merge(opponent, get(payload, 'opponent', {}));
        this.setState({
            board,
            player,
            opponent,
            gameId: payload.id,
            gameSaved: false,
            isLoaded: true,
        });
    }

    startGame() {
        let url = NEW_GAME_URL;
        const urlParameters = getUrlParameters();
        const gameId = get(urlParameters, 'gameId');
        if (gameId) {
            url = `${LOAD_GAME_URL}/${gameId}`;
        }

        Request.to(url)
            .withAuthentication()
            .get()
            .then((payload) => {
                this.updateGameState(payload);
                updateQueryParameters({ gameId: payload.id });
            })
            .catch(error => this.setState({ errors: ['There was an error communicating with the server'] }));
        this.setState({
            canSubmit: false,
        });
    }

    newGame() {
        Request.to(NEW_GAME_URL)
            .withAuthentication()
            .get()
            .then((payload) => {
                this.updateGameState(payload);
                updateQueryParameters({ gameId: payload.id });
            })
            .catch(error => this.setState({ errors: ['There was an error communicating with the server.'] }));
        this.setState({
            canSubmit: false,
        });
    }

    submitTiles() {
        const payload = {
            gameId: this.state.gameId,
            tiles: get(this.state, 'player.submittedTiles', []),
        };
        new Request(PLAY_MOVE_URL)
            .withBody(payload)
            .post()
            .then((payload) => {
                this.updateGameState(payload);
                this.saveGame();
            })
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
        const payload = {
            tiles,
            gameId: this.state.gameId,
        };
        Request.to(VALIDATE_MOVE_URL)
            .withResponseExtractor(response => get(response, 'payload', []))
            .withErrorHandler(alertErrorHandler)
            .withBody(payload)
            .post()
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

    saveGame() {
        if (!this.state.userId) {
            return;
        }

        const payload = {
            gameId: this.state.gameId,
            userId: this.state.userId,
        };
        Request.to(SAVE_GAME_URL)
            .withBody(payload)
            .withAuthentication()
            .post()
            .then((payload) => {
                this.setState({ gameSaved: true });
            });
    }
}