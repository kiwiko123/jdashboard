import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';
import { quietlyGoTo } from 'common/js/urltools';

export default class ChatroomRoomPermissionStateManager extends StateManager {

    constructor(roomId) {
        super();
        this.roomId = roomId;
        this.checkPermissions();
    }

    checkPermissions() {
        Request.to(`/chatroom/api/room/${this.roomId}/permissions`)
            .authenticated()
            .get()
            .then((response) => {
                if (response.canAccess) {
                    this.sendState('ChatroomMessageFeedStateManager', null, 'canAccess');
                } else {
                    quietlyGoTo('/chatroom');
                }
            });
    }
}