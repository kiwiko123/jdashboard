import StateTransmitter from 'state/StateTransmitter';
import { get, identity, set, sumBy } from 'lodash';
import Request from 'common/js/Request';
import logger from 'common/js/logging';
import { getUrlParameters, updateQueryParameters } from 'common/js/urltools';

const PLAYER_ID = 'player';
const OPPONENT_ID = 'opponent';

export default class PazaakGameStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.initialize();
    }

    initialize() {
        this.setState({
            actions: {
                endTurn: this.endTurn.bind(this),
            },
        });

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
            .withResponseExtractor(identity)
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
        updateQueryParameters({ gameId: game.gameId });

        const playerCards = get(game, 'player.placedCards', []);
        const opponentCards = get(game, 'opponent.placedCards', []);

        const playerScore = sumBy(playerCards, card => card.modifier);
        const opponentScore = sumBy(opponentCards, card => card.modifier);

        set(game, 'player.score', playerScore);
        set(game, 'opponent.score', opponentScore);

        this.setState({
            player: game.player,
            opponent: game.opponent,
            gameId: game.gameId,
        });
    }

    endTurn(playerId, { cardModifier } = {}) {
        // Played card is present if the player placed a card from their hand.
        const playedCard = cardModifier ? { modifier: cardModifier } : null;
        const payload = {
            playerId,
            playedCard,
        };

        Request.to(`/pazaak/api/games/${this.state.gameId}/end-turn`)
            .withAuthentication()
            .withBody(payload)
            .withResponseExtractor(identity)
            .post()
            .then((response) => {
                if (response.errorMessage) {
                    // handle errors
                    logger.info(`Pazaak error message: ${response.errorMessage}`);
                } else {
                    this.updateGameState(response.game);
                }
            });
    }
}