$(function(){

    function uninstallCollapseForActiveSnippet(){
        $(".collapse-button",".snippet-wrapper.selected").each(function(){
            $(this).off();
        });
    }

    function uninstallChangeForActiveSnippet(){
        $(".answer", ".snippet-wrapper.selected").each(function(){
            $(this).off();
        });
    }
    
    function addPositionStyle(){
    	$(".snippet-wrapper.selected").each(function(){
    		var left = $(this).data("left");
    		var top = $(this).data("top");
    		$(this).css({
    			"left": left,
    			"top": top
    		});
    	});
    }
    

    function deactiveateActiveSnippet(){
        uninstallChangeForActiveSnippet();
        uninstallCollapseForActiveSnippet();
        addPositionStyle();
    }

    function installCollapseForActiveSnippet() {
        $(".collapse-button", ".snippet-wrapper.selected").each(function () {
            $(this).click(function () {
                var parent = $(this).closest(".bubble").toggleClass("collapsed");
                $(".full-text", parent).toggleClass("hidden");
                $(".collapsed-text", parent).toggleClass("hidden");
                $(this).toggleClass("fa-minus").toggleClass("fa-plus");
            });
        });
    }

    function installChangeForActiveSnippet(){
        $(".answer", ".snippet-wrapper.selected").each(function(){
            $(this).click(function(){
                deactiveateActiveSnippet();
                $(this).closest(".snippet-wrapper.selected").removeClass("selected").addClass("background");
                var attached = $(this).data("attached");
                $("[data-snippet="+attached+"]").removeClass("background").addClass("selected");
                activateActiveSnippet();
            });
        });
    }
    
    function removePositionStyle(){
    	$(".snippet-wrapper.selected").each(function(){
    		$(this).css({
    			"left": "",
    			"top": ""
    		});
    	});
    }
    
    function activateActiveSnippet(){
        installChangeForActiveSnippet();
        installCollapseForActiveSnippet();
        removePositionStyle();
    }

    activateActiveSnippet();
});