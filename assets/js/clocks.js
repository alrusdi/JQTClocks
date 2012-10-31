var jQT = $.jQTouch({
    icon: 'img/icon.png',
    startupScreen: 'img/startup.png',
    useAnimations: false
});

function getClocks()
{
    var clocks = localStorage.getItem("clocks");
    if (clocks)
    {
        return JSON.parse(clocks);
    }
    return [];
}
function addClock(label, tz, skip_save){
    var html = $('#clock_template').html();
    var insert = $(html);
    $('#clocks').append(
        insert.data('tz_offset', tz).find('.city').html(label).end()
    );
    if (!skip_save)
    {
        var data = getClocks();
        data.push({'label':label, 'tz':tz});
        localStorage.setItem("clocks", JSON.stringify(data));
    }
}
function removeClock(t)
{
    var me = $(t).parent();
    var tz_offset = me.data('tz_offset') || 0;
    var clocks = getClocks();
    for (var i in clocks)
    {
        if (clocks[i].tz == tz_offset)
        {
            clocks.splice(i, 1);
            localStorage.setItem("clocks", JSON.stringify(clocks));
            me.remove();
            return;
        }
    }
}
function updateClocks(){
    var localTime = new Date();
    $('#clocks > div').each(function(){
        var tz_offset = $(this).data('tz_offset') || 0;
        var ms = localTime.getTime()
             + (localTime.getTimezoneOffset() * 60000)
             + (tz_offset + 1) * 3600000;
        var time = new Date(ms);
        var hour = time.getHours();
        var minute = time.getMinutes();
        var second = time.getSeconds();
        var $el = $(this);
        var nicehour = hour;
        /*
        if (hour > 12 ) {
            nicehour = hour - 12;
        } else if ( hour == 0 ) {
            nicehour = 12;
        }
        */
        $('.hour', $el).css('-webkit-transform', 'rotate(' + ( hour * 30 + (minute/2) ) + 'deg)');
        $('.min', $el).css('-webkit-transform', 'rotate(' + ( minute * 6 ) + 'deg)');
        $('.sec', $el).css('-webkit-transform', 'rotate(' + ( second * 6 ) + 'deg)');
        if (minute<10){
            minute = '0'+minute;
        }
         if (second<10){
            second = '0'+second;
        }
        $('.time', this).html(nicehour + ':' + minute + ':' + second);
    });
}

$(function(){
    $('#add_clock_btn').click(function(){
        addClock($('#label').val(), Number($('#timezone').val()), false);
        $('#cancel_btn').trigger('tap');
    });
    var clocks = getClocks();
    for (var i in clocks)
    {
        addClock(clocks[i].label, clocks[i].tz, true);
    }
    updateClocks();
    setInterval(updateClocks, 1000);
});