// JavaScript Document
function createBaseLayout() {
        var layout = new YAHOO.widget.Layout({
            units: [
                { position: 'top', height: 105, body: 'header', gutter: '0px', resize: false },    
                { position: 'bottom', height: 20, body: 'archFooter', gutter: '0px', resize: false },
                { position: 'left', header:'Penn Fitness', width: 200, resize: false, body: 'leftMenu', gutter: '0px', collapse: true },
                { position: 'center', minWidth: 400, minHeight: 200, body: 'centerArea', gutter: '1px 0px 0px 1px' }
            ]
        });
        layout.render();
}

YAHOO.util.Event.onDOMReady(createBaseLayout);




