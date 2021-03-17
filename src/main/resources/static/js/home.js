/* 发送一级回复，根据返回值做出相应*/
function post() {
	var questionId = $("#questionId").val();
	var content = editor.txt.html();
	comment2target(questionId, 1, content);

}

/* 发送二级回复，根据返回值做出相应*/
function comment(e) {
	var id = e.getAttribute("data-id");
	var content = $("#input-" + id).val();
	comment2target(id, 2, content);
}

/* 重构Post方法，让发送一级评论和二级评论得以共同使用*/
function comment2target(targetId, type, content) {
	if (!content) {
		alert("不能回复空内容~!");
		return;
	}

	$.ajax({
		type: "POST",
		url: "/comment",
		contentType: "application/json",
		data: JSON.stringify({
			"parentId": targetId,
			"content": content,
			"type": type
		}),
		success: function(response) {
			if (response.code == 200) {
				window.location.reload();
			} else {
				if (response.code == 2003) {
					var isAccepted = confirm(response.message);
					if (isAccepted) {
						var loginUrl = $("#loginUrl").val();
						window.open(loginUrl);
						window.localStorage.setItem("closable", true);
					}
				} else {
					alert(response.message);
				}
			}
		},
		dataType: "json"
	});
}

/* 展开二级评论 */
function collapseComments(e) {
	var id = e.getAttribute("data-id");
	var comments = $("#comment-" + id);
	// 展开二级评论
	comments.toggleClass("show");
	// 评论按钮变为点击状态
	$(e).toggleClass("active");

	// 还是要判断一下当前展开状态
	var collapse = $("#comment-" + id).is('.show');
	var subCommentContainer = $("#comment-" + id);
	
	// 只有展开状态、子元素状态等于2的时候才追加内容（2是因为一个div和一个hr）
	if (collapse == true && subCommentContainer.children().length == 2) {
		// console.log(subCommentContainer.children().length);
		$.getJSON("/comment/" + id, function(data) {
			$.each(data.data.reverse(), function(index, comment) {
				
				var dateElement = $("<span/>", {
					"class" : "mediaText pull-right",
					"html": moment(comment.gmtcreate).format('YYYY-MM-DD hh:mm:ss')
				});
				
				var pElement = $("<div/>", {
					"style" : "margin-left: 10px; font-size:14px",
					html: comment.content
				});
				
				var h6Element = $("<h6/>", {
					"class" : "media-heading",
					"style" : "margin-left: 10px",
					html: comment.user.name
				});
				
				var mediaBodyElement = $("<div/>", {
					"class": "media-body",
				}).append(h6Element).append(pElement);
				
				var imgElement = $("<img/>", {
					"class": "media-object img-thumbnail",
					"src" : comment.user.avatarurl,
					"style" : "height: 50px;"
				});
				
				var aElement = $("<a/>", {
					"href": '/userinfo/' + comment.user.name,
				}).append(imgElement);
				
				var mediaElement = $("<div/>", {
					"class": "media",
					"style" : "margin-top: 10px",
					// html: comment.content
				}).append(aElement).append(mediaBodyElement).append(dateElement);
				
				subCommentContainer.prepend(mediaElement);
				
			});
		});
	}
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if (previous) {
        var index = 0;
        var appear = false; //记录value是否已经作为一个独立的标签出现过
        while (true) {
            index = previous.indexOf(value, index); //value字符串在previous中出现的位置
            if (index == -1) break;
            //判断previous中出现的value是否是另一个标签的一部分
            //即value的前一个和后一个字符都是逗号","或者没有字符时，才说明value是一个独立的标签
            if ((index == 0 || previous.charAt(index - 1) == ",")
                && (index + value.length == previous.length || previous.charAt(index + value.length) == ",")
               ) {
                appear = true;
                break;
            }
            index++; //用于搜索下一个出现位置
        }
        if (!appear) {
            //若value没有作为一个独立的标签出现过
            $("#tag").val(previous + ',' + value);
        }
    }
    else {
        $("#tag").val(value);
    }
}


