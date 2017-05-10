window.addEventListener('load', function () {
    $(".creditCard").on("click", function (ev) {
        if (ev.target != ev.currentTarget) { return; }

        let showCC = $(this).find(".showCC");

        let visible = showCC.is(":visible");

        $(".showCC").;

        showCC.toggle(!visible);


    });
});