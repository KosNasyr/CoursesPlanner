function students_id(template, id) {
    $.ajax({
        url: "student/" + id,
        type: "GET"
    }).done(function(data){
        $.ajax({
        url: "course/all",
        type: "GET"
        }).done(function(courses){
            dust.renderSource(template, {data, courses} , function(err,out) {
                $("#content").empty().append(out);

                $("#saveChangesStudentBtn").click(function(){
                    var fullName = $('input[name=username]').val();
                    var email = $('input[name=email]').val();
                    var telNumber = $('input[name=number]').val();

                    $.ajax({
                        method: "PUT",
                        url: "/student/" + data.id,
                        dataType: 'json',
                        contentType: "application/json; charset=utf-8",
                        data:   JSON.stringify({
                              "fullName": fullName,
                              "email": email,
                              "telNumber": telNumber
                            })
                    }).done(function(){

                    }).fail(function(XNR) {

                    });
                });
                
                $("#scheduleStudentBtn").click(function(){
                      var newCourse = $("#coursesDropdown").val();

                    $("#scheduleModal").modal("hide");
                    $("#coursesDropdown").val("");
                    $.ajax({
                        method: "PUT",
                        url: "/course/update/" + newCourse + "," + data.id

                    }).done(function(){

                    }).fail(function(XNR) {

                    });
                });
                
                $("#deleteStudentBtn").click(function(){

                    $("#deleteModal").modal("hide").on('hidden.bs.modal', function (e) {
                        window.location.href='#students/';
                    })
                    $.ajax({
                       method: "DELETE",
                        url: "/student/" + data.id,
                    }).done(function(){

                    }).fail(function(XNR) {

                    });
                })
            }); 
        }).fail(function(XNR){
            console.error("Не удалось загрузить данные");
        });
        
    }).fail(function(XNR){
        console.error("Не удалось загрузить данные");
    });
}


