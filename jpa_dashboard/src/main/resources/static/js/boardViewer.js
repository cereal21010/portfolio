$(document).ready(function() {

	var indent = $('.indent:last').val();
	var url = window.location.href;
	
	//답글의 경우 들여쓰기
	$('.cIndent').each(function(i,e){
		if($(this).val() == 0){
			$(this).parent().css('text-indent','0em');
		} else {
			$(this).parent().css('text-indent','3em');
		}
	});
	
	//댓글 작성자의 경우에만 수정, 삭제 가능
	$('.cWriter').each(function(i,e){
		if($('.loginUser').val() == $(this).text()){
			$(this).next().css('display','');
		} else {
			$(this).next().css('display','none');
		}
	});
	
	//게시글 작성자만 수정, 삭제 가능
	$('.boardModify').css('display',$('.writerCheck').val());

	//대댓글 작성 기능
	$('.comment_write_button').on('click', function(){
	
		var clickId = $(this).parent().parent().attr('id');
		var commentFlag = $(this).attr('class');
		var groups = $(this).parent().siblings('input').val();
		
		if(commentFlag == 'comment_write_button'){
			$('.comment_list .reCommentForm').remove();
			$('.comment_list').find('.comment_cancel_button').text('답글쓰기');
			$('.comment_list').find('.comment_cancel_button').attr('class','comment_write_button');
			$('.commentForm').clone().appendTo('#'+clickId);
			$('#'+clickId+' form').attr('class','reCommentForm');
			$('#'+clickId+' form div input').val(groups);
			$(this).text('답글취소');
			$(this).attr('class','comment_cancel_button');
		} else {
			$('#'+clickId+' form').remove();
			$(this).text('답글쓰기');
			//alert($('#'+clickId).children().children('a.comment_write_button'));
			$(this).attr('class','comment_write_button');
		}
	});
	
	
	//게시글 삭제 기능
	$('.del-btn').on('click', function(){
		var result = confirm("삭제 하시겠습니까?");
		if(result){
			document.boardDelete.submit();
		} else {
			return;
		}
	});
	   
	   
	//댓글 삭제 기능
	$('.commentDelete').on('click', function(){
	
		var bSeq = $('.boardSeq').val();
		var cSeq = $(this).attr('id');

		var result = confirm("삭제 하시겠습니까?");
		if(result){
			$.ajax({
				url : "/board/commentDelete" ,
				type : "post" ,
				contentType: "application/json; charset=utf-8" ,
				data : JSON.stringify({"bSeq" : bSeq, "cSeq" : cSeq}) ,
				success : function(){
					location.reload();
				} ,
				error : function(e){
					console.log(e);
				}
			
			
			});
		} else {
			return;
		}
	});       
	
	//댓글 수정 기능
	$('.commentEdit').on('click', function(){
		var form = $('.commentForm');	//댓글 입력하는 form
		var clickSeq = $(this).parent().parent().attr('id');	//선택한 댓글의 seq번호
		var cContents = $('#'+clickSeq+' .cContents').text();	//선택한 댓글의 내용
		var boardSeq = $('.boardSeq').val();	//게시글의 seq번호
		
		//$('#'+clickSeq).empty();
		$('.comment_list div').css('display','');
		$('#'+clickSeq+' div').css('display','none');	//클릭한 댓글의 div의 내용을 다 안보이게 한다
		$('.comment_list .commentEditForm').remove();	//답글쓰기을 선택할 상태에서 다른 댓글에 답글쓰기를 누를 경우 원래 있던 수정할 댓글작성란을 없애기 위해 전부 지워준다
		$(form).clone().appendTo('#'+clickSeq);	//선택한 li에 댓글을 수정할 수 있는 form을 달아준다
		$('#'+clickSeq+" form").attr('class','commentEditForm');	// li에 달아준 form에 class를 붙여준다
		$('.commentEditForm').attr({action : '/board/commentUpdate/'+boardSeq, method : 'post'});	//li에 달아준 form 에 action과 method 속성을 달아준다
		$('.commentEditForm .cFormSeq').val(clickSeq);	//li에 달아준 form의 hidden속성인 input에 선택한 댓글의 seq 값을 넣어준다
		
		//댓글 수정 form에는 취소 버튼을 추가해준다 
		$('<button type="button" class="btn btn-danger mb-3 commentEdit_cancel">취소</button>').clone().appendTo('#'+clickSeq+' .form-group');
		$('.commentEditForm textarea').text(cContents);	//댓글 수정 form에 원래의 댓글 내용을 넣어준다
		
		//댓글 수정 버튼 클릭시에도 다른글에 수정,삭제 버튼이 나오는 것을 방지하기위해 추가
		$('.cWriter').each(function(i,e){
			if($('.loginUser').val() == $(this).text()){
				if( $(this).parent().attr('id') == clickSeq ){
					$(this).next().css('display','none');
				} else{
					$(this).next().css('display','');
				}
			} else {
				$(this).next().css('display','none');
			}
		});
		
	});
	
	//댓글수정 중 취소버튼을 누를 경우 새로고침
	$(document).on('click', '.commentEdit_cancel', function(event){
		location.reload();
	});
	
	
	
	
}); 