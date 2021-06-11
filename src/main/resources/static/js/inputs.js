let camposMaximo = 11;
let i = 2;

$('#add_field').click(function (e) {
    e.preventDefault();
    if (i < camposMaximo) {
        $('#listas').append('<div class="row">\
                                <div class="col-md-10">\
                                   <input type="text" name="response" placeholder="Response '+ i +'" class="form-control input" required />\
                                </div>\
                                <a href="#" class="remover_campo"><img src="images/delete1.png"></a>\
                            </div>');
        i++;
    }
});

$('#listas').on("click", ".remover_campo", function(e){
    e.preventDefault();//previniendo clicks
    $(this).parent('div').remove();
    i--;
});
