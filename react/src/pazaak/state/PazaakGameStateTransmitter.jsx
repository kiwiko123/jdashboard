import StateTransmitter from 'state/StateTransmitter';
import { get, set, sumBy } from 'lodash';
import Request from 'common/js/Request';
import { getUrlParameters, updateQueryParameters } from 'common/js/urltools';

const PLAYER_ID = 'player';
const OPPONENT_ID = 'opponent';

export default class PazaakGameStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.initialize();
    }

    initialize() {
        const { gameId } = getUrlParameters();
        if (gameId) {
            this.loadGame(gameId);
            return;
        }

        this.createNewGame();
    }

    createNewGame() {
        const payload = {
            playerId: PLAYER_ID,
            opponentId: OPPONENT_ID,
        };

        Request.to('/pazaak/api')
            .withAuthentication()
            .withResponseExtractor(e => e)
            .withBody(payload)
            .post()
            .then((game) => this.updateGameState(game));
    }

    loadGame(gameId) {
        Request.to(`/pazaak/api/games/${gameId}`)
            .withAuthentication()
            .withResponseExtractor(e => e)
            .get()
            .then((game) => {
                if (game) {
                    this.updateGameState(game);
                } else {
                    this.createNewGame();
                }
            });
    }

    updateGameState(game) {
        const { gameId } = game;
        if (gameId) {
            updateQueryParameters({ gameId });
        }

        const playerCards = get(game, 'player.placedCards', []);
        const opponentCards = get(game, 'opponent.placedCards', []);

        const playerScore = sumBy(playerCards, card => card.modifier);
        const opponentScore = sumBy(opponentCards, card => card.modifier);

        set(game, 'player.score', playerScore);
        set(game, 'opponent.score', opponentScore);

        this.setState({
            player: game.player,
            opponent: game.opponent,
        });
    }
}