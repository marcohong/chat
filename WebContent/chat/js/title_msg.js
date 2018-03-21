var titleMsg = {
	time: 0,   
    title: parent.document.title,   
    timer: null,
    
    //显示提示消息
    show:function() {
    	var title = titleMsg.title.replace("【　　　】", "").replace("【新消息】", "");
    	titleMsg.timer = setTimeout( 
    			function() {
    				titleMsg.time++;
    				titleMsg.show();
    				if(titleMsg.time % 2== 0) {
    					parent.document.title = "【新消息】" + title;
    				}else {
    					parent.document.title = "【　　　】" + title;
    				}
    			},400
    	);
    	return [titleMsg.timer, titleMsg.title];
    },

	clear:function() {
		 clearTimeout(titleMsg.timer);
		 parent.document.title = titleMsg.title;
	}
}
