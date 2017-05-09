function getTime()
{
    var d = new Date("Thu, 20 Apr 2017 05:33:52 GMT");
    //Thu, 20 Apr 2017 05:33:52 GMT
    var n = d.getTimezoneOffset();
    var offsetMs = n * 60000;
    alert('The org time is ' + d);  // 顯示原始從server得到的時間 Thu, 20 Apr 2017 05:33:52 GMT
    alert('getTimezoneOffset: ' + n); //顯示getTimezoneOffset
    alert('The new time is ' + new Date(d.getTime() + offsetMs)); // 顯示調整時差過後的時間
}