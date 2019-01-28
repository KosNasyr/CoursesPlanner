function students(template) {
    $.ajax({
        url: "/student/all",
        type: "GET"
    }).done(function(data){
       dust.renderSource(template, data, function(err,out) {
        $("#content").empty().append(out);
           
        $("#newStudentBtn").click(function(){
           var fullName = $('input[name=username]').val();
            var email = $('input[name=email]').val();
            var telNumber = $('input[name=number]').val();
        
            if(fullName != "" && email != "" && telNumber != "") {
                addStudent(fullName, email, telNumber);
                $("#newStudentModal").modal("hide").on('hidden.bs.modal', function (e) {
                    window.location.href='#students/';
                });
                $("input[name=username], input[name=email], input[name=number]").val("");
                $(".form-group input").removeClass("border border-danger");
               $(".alert-danger").removeClass("show");
            } else {
                $(".form-group input").addClass("border border-danger");
               $(".alert-danger").addClass("show");
            }
           
        });

    });
        
        
    }).fail(function(XNR){
        console.error("Не удалось загрузить данные");
    });
    
    function addStudent(fullName, email, telNumber) {
        $.ajax({
            method: "POST",
            url: "/student/add",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data:   JSON.stringify({
                      "fullName": fullName,
                      "email": email,
                      "telNumber": telNumber
                    })
        }).done(function(){
            window.location.href='#students/';
        }).fail(function(XNR) {
            
        });
    }
}
