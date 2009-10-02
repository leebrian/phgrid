function random_color(format)
{
 var rint = Math.round(0xffffff * Math.random());
 switch(format)
 {
  case 'hex':
   return ('#0' + rint.toString(16)).replace(/^#0([0-9a-f]{6})$/i, '#$1');
  break;

  case 'rgb':
   return 'rgb(' + (rint >> 16) + ',' + (rint >> 8 & 255) + ',' + (rint & 255) + ')';
  break;

  default:
   return rint;
  break;
 }
}