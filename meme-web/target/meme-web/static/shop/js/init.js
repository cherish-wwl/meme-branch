!(function (doc, win) {
	function initSize() {
        var w = el.clientWidth;
        if (!w) return;
        w=w>480?480:w;
        w=w<320?320:w;
        el.style.fontSize = (100 * (w / 1080)).toFixed(3) + 'px';
    }
    
    var el = doc.documentElement;
    if (!doc.addEventListener) return;
    initSize();
    win.addEventListener('resize', initSize, false);
    win.addEventListener('pageshow', function(e) {
         if (e.persisted) {
        	initSize();
         }
    }, false);
})(document, window);
