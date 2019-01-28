function courses(template) {
    $.ajax({
        url: "course/all",
        type: "GET"
    }).done(function(data){
       dust.renderSource(template, data, function(err,out) {
        $("#content").empty().append(out);
        });
        
        $(".course").click(function(){
            window.location.href='#courses/' + this.dataset.id;
        });
        
        $("#addCourseBtn").click(function() {
            let courseTitle = $('input[name=courseTitle]').val(),
                beginning = $('input[name=beginning]').val(),
                ending = $('input[name=ending]').val(),
                daysCount = daysBetween(new Date(beginning), new Date(ending));
        
            if (courseTitle !== "" && beginning !== "" && ending !== "" && daysCount !== "") {
                addCourse(courseTitle, beginning, ending, daysCount);
                $("#courseAddModal").modal("hide").on('hidden.bs.modal', function () {
                    window.location.href='#courses/';
                });
                $("input[name=courseTitle], input[name=beginning], input[name=ending]").val("");
                $(".form-group input").removeClass("border border-danger");
               $(".alert-danger").removeClass("show");
            } else {
                console.log("yo");
                $(".form-group input").addClass("border border-danger");
               $(".alert-danger").addClass("show");
            }
           
        });

    }).fail(function(){
        console.error("Не удалось загрузить данные");
    });
    function daysBetween(one, another) {
        return one > another ? "" :  Math.round(Math.abs((+one) - (+another))/8.64e7);
    }
    
    function addCourse(courseTitle, beginning, ending, daysCount) {
        $.ajax({
            method: "POST",
            url: "/course/add",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data:   JSON.stringify({
                      "beginning": beginning,
                      "courseTitle": courseTitle,
                      "daysCount": daysCount,
                      "ending": ending
                    })
        }).done(function(){
            console.log("sent");
        }).fail(function(XNR) {
            
        });
    }
   
}