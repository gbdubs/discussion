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

    function deactiveateActiveSnippet(){
        uninstallChangeForActiveSnippet();
        uninstallCollapseForActiveSnippet();
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

    function activateActiveSnippet(){
        installChangeForActiveSnippet();
        installCollapseForActiveSnippet();
    }

    activateActiveSnippet();
});