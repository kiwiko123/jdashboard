
function isOutboundMessage(userId, message) {
    return message.senderUserId === userId;
}

function isInboundMessage(userId, message) {
    return message.recipientUserId === userId;
}

export default {
    isOutboundMessage,
    isInboundMessage,
};