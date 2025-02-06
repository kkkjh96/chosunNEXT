var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "smarteditor", // 에디터가 들어갈 ID
    sSkinURI: "./SmartEditor2Skin.html",  // 스킨 파일 위치 (현재 HTML 파일과 같은 폴더)
    fCreator: "createSEditor2"
});
