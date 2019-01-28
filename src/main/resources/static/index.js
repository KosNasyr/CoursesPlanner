var $app = $("#app");

$(function () {
    $(document).ready(function () {
        loadMenu();
        $(window).on("hashchange", loadContent);
    });
    
    function loadMenu() {
        
        loadTemplate(["menu"], undefined, function(template) {
            dust.renderSource(template, {}, function(err, out) {
              $app.empty().append(out);
               loadScript(["menu"]);
               loadContent();
           }); 
        });
    };
    
    function loadContent() {
        var name;
        var id;
        if (location.hash !== "") {
            var path = location.hash.split("/");
            name = path[0].substr(1);
            if(path.length > 1) {
                id = isNumber(path[1]);
            }  
        } else {
            name = "dashboard";
        }

        $(".nav-link").removeClass("active");
        $('a[href="#' + name + '"]').addClass("active"); 
        
        loadTemplate(name, id, function(template) {
             loadScript(name, id, template);
        });   
    };
}).ajaxError(function(event, jqXHR) {
    if(jqXHR.status === 401) {
        login();
    }
});


function loadTemplate(name, id, successCallback, failCallback) {
    var address = name;
    address += !id ? "" : "_id" ;
    $.ajax({ 
        url: "components/" + name + "/" + address + ".dust"
    }).done(function(template){
        successCallback ? successCallback(template) : null;
    }).fail(function(XNR){
        console.error("Не удалось найти " + name + ".dust");
        failCallback ? failCallback(XHR) : null;
    });
};

function loadScript(name, id, template, failCallback) {
    var address = name;
    address += !id ? "" : "_id" ;
    $.ajax({
        url: "components/" + name + "/" + address + ".js"
    }).done(function(script){
        script ? window[address](template, id) : null;
    }).fail(function(XNR){
        console.error("Не удалось найти " + name + ".js");
        failCallback ? failCallback(XHR) : null;
    });
};

function login() {
    loadTemplate(["login"], undefined, function(template) {
        dust.renderSource(template, {}, function(err, out) {
            $app.empty().append(out);
        })
    })
}

function isNumber(input) {
    if(!isNaN(input)){
        return +input;
    }
}

