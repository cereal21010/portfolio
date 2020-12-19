$(document).ready(function() {
	var sPageURL = window.location.search.substring(1), //string?
		sURLVariables = sPageURL.split('&'); //list
	
	$('.page-link').click(function () {
		var value = $(this).attr('data-value');;
		if (!sPageURL) {
		//url에 아무 변수도 존재하지 않으면 실행
			window.location.href = window.location.href + '?page=' + value
		} else {
		//url에 page외에도 어떠한 변수가 있다면 실행
			for (var i = 0; i < sURLVariables.length; i++) {
				var sParameter = sURLVariables[i].split('='); //list
				//url 변수중에 page가 첫 번째 변수 일 경우 실행
				if (sParameter[0] === 'page') {
					window.location.href = window.location.href.replace('page=' + sParameter[1], 'page=' + value);
					return;
				}
			}
			//url에 page말고 다른 변수만 있을 때 실행
			window.location.href = window.location.href + '&page=' + value
		}
	});


});