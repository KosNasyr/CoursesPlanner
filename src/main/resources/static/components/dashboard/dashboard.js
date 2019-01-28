function dashboard(template) {

    $.ajax({
        url: "student/all",
        type: "GET"
    }).done(function(studentsData){
        $.ajax({
            url: "course/all",
            type: "GET"
        }).done(function(data){
            var students_counter = studentsData.length;
            var courses = 0;
            var passed_counter = 0;
            var future_counter = 0;
            var now = new Date();
            data.forEach(function(course) {
                let start = new Date(course.beginning.split("-"));
                let end = new Date(course.ending.split("-"));
                if (end < now) {
                  passed_counter++;  
                } else {
                  courses++;  
                }
                if(start > now) {
                  future_counter++;  
                }
            });

            dust.renderSource(template, {students_counter, courses, passed_counter, future_counter}, function(err,out) {
                $("#content").empty().append(out);
            });
            
            
        }).fail(function(XHR){
            console.error("Не удалось загрузить данные");
        });
    }).fail(function(XHR){
        console.error("Не удалось загрузить данные");
    });
    
    
}
