$(function () {
    $('#more_news').click(function () {
        var request = $.ajax({
            url: "getMainNews"
        });
        request.done(function( msg ) {
            $("#news").append( msg );
        });
        request.fail(function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    });
});