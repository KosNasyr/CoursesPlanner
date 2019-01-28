function courses_id(template, id) {
    var marks;
    $.ajax({
        url: "course/" + id,
        type: "GET"
    }).done(function(data){
       marks = data.progresses.map(function (item) {
         return {marks: item.marks, studentId: item.studentId};
       });

        startDate = new Date(data.beginning);
        endDate = new Date(data.ending);

        var dateArray = getDateArray(startDate, endDate);

        $.ajax({
            url: "student/all",
            type: "GET"
        }).done(function(students){

            dust.renderSource(template, {data, students, dateArray}, function(err,out) {
                $("#content").empty().append(out);

                $mark = $(".markSelect");
                $mark.unbind("change");
                $mark.on("change", function(){
                   var column = $(this).closest("td").index() - 2;
                   var mark = $(this).val();
                   var id = $(this).attr("data-id");
                    marks = insertMark(mark, id, column, marks);
               });

                $("#saveMarksBtn").click(function () {
                    console.log("передаем" + marks);
                   $.ajax({
                       method: "PUT",
                       url: "/course/update_marks/" + id,
                       dataType: 'json',
                       contentType: "application/json; charset=utf-8",
                       data:   JSON.stringify(marks)
                   }).done(function () {
                   }).fail(function(XNR) {

                   });
                });

                $("#addStudentBtn").click(function(){
                    var student = $("#studentsDropdown").val();
                    $("#addStudentModal").modal("hide").on("hidden.bs.modal", function (e) {
                        window.location.href='#courses/' + id + "/";
                    });
                    $("#coursesDropdown").val("");
                    $.ajax({
                        method: "PUT",
                        url: "/course/update/" + id + "," + student
                    }).done(function(){

                    }).fail(function(XNR) {

                    });
                });

                $("#deleteCourseBtn").click(function(){

                    $("#deleteCourseModal").modal("hide").on('hidden.bs.modal', function (e) {
                        window.location.href='#courses/';
                    })
                    $.ajax({
                        method: "DELETE",
                        url: "/course/" + id
                    }).done(function(){

                    }).fail(function(XNR) {

                    });
                });

                $(".close").click(function () {
                    var studentToDelete;
                   studentToDelete = $(this).attr("data-id");
                    $.ajax({
                        method: "PUT",
                        url: "/course/delete_from/" + id + "," + studentToDelete
                    }).done(function(){
                        window.location.href='#courses/' + id ;
                    }).fail(function(XNR) {

                    });
                });

            }); 
        }).fail(function(XNR){
            console.error("Не удалось загрузить данные");
        });
    }).fail(function(XNR){
        console.error("Не удалось загрузить данные");

    });

    function insertMark(mark, id, position, marksArray) {
       marksArray.forEach(function(item){
            if (item.studentId === +id) {
             item.marks[position] = +mark;
            }
        });
        return marksArray;
    }

    function getDateArray(start, end) {
       var arr = new Array();
       var dt = new Date(start);

        while (dt <= end) {

            arr.push(formatDate(new Date(dt)));
            dt.setDate(dt.getDate() + 1);
        }
        return arr;
    }

    function formatDate(date) {
        var day = date.getDate();
        var monthIndex = date.getMonth();
        var year = date.getFullYear();

        return day + '-' + monthIndex;
    }
}
