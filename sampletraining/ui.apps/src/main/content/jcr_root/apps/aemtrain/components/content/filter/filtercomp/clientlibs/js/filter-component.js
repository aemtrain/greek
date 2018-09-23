
$(window).on('load', function() {

    var filterJson = getFilterAttributes();
    console.log(JSON.stringify(filterJson));

});

function getFilterAttributes(){


    jsonObject = {};
	$('input:hidden').each(function(){
        var filter = $(this).attr("data-filter");
        var title = $(this).attr("data-title");
        var reset = $(this).attr("data-reset");
        var more = $(this).attr("data-more");

        item = {};
        item ["title"] = title;
        item ["reset"] = reset;
        item ["more"] = more;

        jsonObject [filter] = item;
    });

    return jsonObject;

}
