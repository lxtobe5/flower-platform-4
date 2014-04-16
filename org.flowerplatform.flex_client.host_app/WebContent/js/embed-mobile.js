/**
 * Invokes a callback function on the Flex mobile app.
 * 
 * @param callback
 * @param args
 */
function callFlexCallback(callback /* : String */, args) /* : Function */ {
	StageWebViewBridge.call(callback, null, JSON.stringify(args));
}

function overrideMeHandler() {
	$("#override").text("Override from Flex mobile! Original is in test.js");
}

//function setJSessionId(id) {
//	jSessionId = id;
//}