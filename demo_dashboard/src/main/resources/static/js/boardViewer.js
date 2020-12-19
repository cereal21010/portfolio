$(document).ready(function() {

	var indent = $('.indent:last').val();
	var url = window.location.href;

	$('.cIndent').each(function(i,e){
		if($(this).val() == 0){
			$(this).parent().css('text-indent','0em');
		} else {
			$(this).parent().css('text-indent','3em');
		}
	});
	
	$('.cWriter').each(function(i,e){

		if($('.loginUser').val() == $(this).text()){
			$(this).next().css('display','');
		} else {
			$(this).next().css('display','none');
		}
	
	});
	
	$('.boardModify').css('display',$('.writerCheck').val());

    
    
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
	
	
	
	$('.del-btn').on('click', function(){
		var result = confirm("삭제 하시겠습니까?");
		if(result){
			document.boardDelete.submit();
		} else {
			return;
		}
	});
	   
	   
	   
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
	
	$('.commentEdit').on('click', function(){
		var form = $('.commentForm');
		var clickId = $(this).parent().parent().attr('id');
		var cContents = $('#'+clickId+' .cContents').text();
		var boardSeq = $('.boardSeq').val();
		
		//$('#'+clickId).empty();
		$('.comment_list div').css('display','');
		$('#'+clickId+' div').css('display','none');
		$('.comment_list .commentEditForm').remove();
		$(form).clone().appendTo('#'+clickId);
		$('#'+clickId+" form").attr('class','commentEditForm');
		$('.commentEditForm').attr({action : '/board/commentUpdate/'+boardSeq, method : 'post'});
		$('.commentEditForm .cFormSeq').val(clickId);
		$('<button type="button" class="btn btn-danger mb-3 commentEdit_cancel">취소</button>').clone().appendTo('#'+clickId+' .form-group');
		$('.commentEditForm textarea').text(cContents);
	});
	
	
	$(document).on('click', '.commentEdit_cancel', function(event){
		location.reload();
	});
	
	
	
	
}); 