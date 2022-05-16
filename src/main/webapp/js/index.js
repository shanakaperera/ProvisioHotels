$(function () {

        $("#check-in").dateRangePicker({
          autoClose: true,
          startDate: new Date(),
          format: DATE_FORMAT,
          selectForward: true,
          applyBtnClass: "btn btn-primary",
          container: ".check-in-dp",
          inline: true,
          singleMonth: true,
          singleDate : true,
          showShortcuts: false,
          showDateFilter: function (time, date) {
            return (
                    '<div style="padding:5px;">\
                                <span style="">' +
                    date +
                    "</span>\
                            </div>"
            );
          },
          customOpenAnimation: function (cb) {
            $(this).fadeIn(300, cb);
          },
          customCloseAnimation: function (cb) {
            $(this).fadeOut(300, cb);
          },
        }).bind('datepicker-change', function (event, obj) {

          $("#check-out").val('');
          	
			const tomorrow = new Date(moment(obj.value, DATE_FORMAT));
			tomorrow.setDate(tomorrow.getDate() + 1);
			//console.log(tomorrow);
			
          $("#check-out").dateRangePicker({
            autoClose: true,
            format: DATE_FORMAT,
            startDate: obj.value ? tomorrow : new Date(),
            //startDate: '2023-01-05',
            selectForward: true,
            applyBtnClass: "btn btn-primary",
            container: ".check-out-dp",
            inline: true,
            singleMonth: true,
            singleDate : true,
            showShortcuts: false,
            showDateFilter: function (time, date) {
              return (
                      '<div style="padding:5px;">\
                                  <span style="">' +
                      date +
                      "</span>\
                              </div>"
              );
            },
            customOpenAnimation: function (cb) {
              $(this).fadeIn(300, cb);
            },
            customCloseAnimation: function (cb) {
              $(this).fadeOut(300, cb);
            },
          });

        });

      });