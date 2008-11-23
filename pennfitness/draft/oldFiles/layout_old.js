// JavaScript Document
function createBaseLayout() {
        var layout = new YAHOO.widget.Layout({
            units: [
                { position: 'top', height: 50, body: 'archHeader', header: 'Header defined by Architecture', gutter: '0px', resize: false },    
                { position: 'bottom', header: 'Footer defined by Architecture', height: 40, body: 'archFooter', gutter: '0px', resize: false },
                { position: 'center', minWidth: 400, minHeight: 200 }
            ]
        });
        layout.on('render', function() {
            var el = layout.getUnitByPosition('center').get('wrap');
            var layout2 = new YAHOO.widget.Layout(el, {
                parent: layout,
//                minWidth: 400,
//                minHeight: 200,
                units: [
                    { position: 'top', height: 30, gutter: '1px 0px 0px 1px', body: 'top menu bar'},
                    { position: 'left', header: 'Penn Fitness', width: 200, resize: false, body: 'Left Menu Panels', gutter: '0px', collapse: true },
                    { position: 'center', body: 'center2', gutter: '1px 0px 0px 1px'}
                ]
            });
            layout2.render();
        });
        layout.render();
}

YAHOO.util.Event.onDOMReady(createBaseLayout);