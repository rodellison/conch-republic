package com.rodellison.conchrepublic.backend.utils;

import com.rodellison.conchrepublic.backend.managers.EventsList;
import com.rodellison.conchrepublic.backend.model.EventItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Extract Web Events Util should")
public class ExtractWebEventsUtilShould {

    private static final Logger log = LogManager.getLogger(ExtractWebEventsUtilShould.class);
    @Test
    @DisplayName("convert the raw html to and EventList full of Events")
    void convertRawHTMLToEventList() {

        ExtractWebEventsUtil myExtractWebEventsUtil = new ExtractWebEventsUtil();

        ArrayList<String> rawHTMLDataTest = new ArrayList<>();

        //contains one div for listing block with no image, and another that does contain an image
        String testHTML =
                "<!doctype html>\n" +
                        "<html class=\"no-js\" lang=\"en-US\">\n" +
                        "<head>\n" +
                        "\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\">(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start': \n" +
                        "new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0], \n" +
                        "j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src= \n" +
                        "'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f); \n" +
                        "})(window,document,'script','dataLayer','GTM-KLKWT3');</script>\n" +
                        "\n" +
                        "\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\">\n" +
                        "!function(f,b,e,v,n,t,s)\n" +
                        "{if(f.fbq)return;n=f.fbq=function(){n.callMethod?\n" +
                        "n.callMethod.apply(n,arguments):n.queue.push(arguments)};\n" +
                        "if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';\n" +
                        "n.queue=[];t=b.createElement(e);t.async=!0;\n" +
                        "t.src=v;s=b.getElementsByTagName(e)[0];\n" +
                        "s.parentNode.insertBefore(t,s)}(window,document,'script',\n" +
                        "'https://connect.facebook.net/en_US/fbevents.js');\n" +
                        "fbq('init', '482626052232956'); \n" +
                        "fbq('track', 'PageView');\n" +
                        "</script>\n" +
                        "\n" +
                        "<meta charset=\"utf-8\">\n" +
                        "<meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                        "<title>Florida Keys & Key West events from the Official Florida Keys Tourism Council</title>\n" +
                        "<meta name=\"description\" content=\"Search the Florida Keys & Key West events calendar from the Official Florida Keys Tourism Council. View Florida Keys events and activities by month and region - Key West, Lower Keys, Marathon, Islamorada and Key Largo.\">\n" +
                        "<meta name=\"keywords\" content=\"Fantasy Fest, Key West events, Key West fantasy fest, Florida Keys events, Key West activities, Key West calendar, Events Key West\">\n" +
                        "<meta name=\"apple-itunes-app\" content=\"app-id=345919968\">\n" +
                        "<meta property=\"og:title\" content=\"Florida Keys & Key West events from the Official Florida Keys Tourism Council\">\n" +
                        "<meta property=\"og:description\" content=\"Search the Florida Keys & Key West events calendar from the Official Florida Keys Tourism Council. View Florida Keys events and activities by month and region - Key West, Lower Keys, Marathon, Islamorada and Key Largo.\">\n" +
                        "<meta property=\"og:image\" content=\"https://fla-keys.com/img/main/fla-keys-preview.jpg\">\n" +
                        "<meta property=\"og:site_name\" content=\"The Florida Keys & Key West\">\n" +
                        "<meta property=\"og:type\" content=\"article\">\n" +
                        "<meta property=\"og:locale\" content=\"en_US\">\n" +
                        "<meta property=\"og:type\" content=\"website\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "<meta name=\"robots\" content=\"noindex, nofollow\">\n" +
                        "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Lato:300,400,400italic,700,700italic\">\n" +
                        "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                        "<link rel=\"stylesheet\" href=\"/css/normalize.css\">\n" +
                        "<link rel=\"stylesheet\" href=\"/css/main.css?20191115\">\n" +
                        "<link rel=\"stylesheet\" href=\"/css/sidr.css\">\n" +
                        "<link rel=\"stylesheet\" href=\"/css/slick.css\">\n" +
                        "<link rel=\"stylesheet\" href=\"/css/swipebox.min.css\">\n" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"/lightbox/lightbox.css\">\n" +
                        "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/gdpr2.css\">\n" +
                        "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"https://cdn.jsdelivr.net/jquery.sidr/2.2.1/jquery.sidr.min.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"/js/slick.min.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"/js/jquery.swipebox.min.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"/js/plugins.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"/js/site.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src='https://app.icontact.com/icp/static/form/javascripts/validation-captcha.js' type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script src=\"/js/vendor/modernizr-2.8.3.min.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script async=\"async\" src=\"https://www.googletagservices.com/tag/js/gpt.js\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\">\n" +
                        "\tvar googletag = googletag || {};\n" +
                        "\tgoogletag.cmd = googletag.cmd || [];\n" +
                        "\t(function() {\n" +
                        "\tvar gads = document.createElement('script');\n" +
                        "\tgads.async = true;\n" +
                        "\tgads.type = 'text/javascript';\n" +
                        "\tvar useSSL = 'https:' == document.location.protocol;\n" +
                        "\tgads.src = (useSSL ? 'https:' : 'http:') +\n" +
                        "\t\t'//www.googletagservices.com/tag/js/gpt.js';\n" +
                        "\tvar node = document.getElementsByTagName('script')[0];\n" +
                        "\tnode.parentNode.insertBefore(gads, node);\n" +
                        "})();\n" +
                        "\n" +
                        "googletag.cmd.push(function() {\n" +
                        "var mapLeader = googletag.sizeMapping().\n" +
                        "\taddSize([0, 0],[320, 50]).\n" +
                        "\taddSize([320, 400], [320, 50]).\n" +
                        "\taddSize([480, 200], [320, 50]).\n" +
                        "\taddSize([728, 200], [728, 90]).\n" +
                        "\tbuild();\n" +
                        "window.LeaderSlot = googletag.defineSlot('/2610296/mctdc-fk-calendarofevents-1', [[320, 50], [728, 90]], 'div-gpt-ad-1478557658967-13').\n" +
                        "\tdefineSizeMapping(mapLeader).\n" +
                        "\taddService(googletag.pubads()); window.LeaderSlot = googletag.defineSlot('/2610296/mctdc-fk-calendarofevents-2', [[320, 50], [728, 90]], 'div-gpt-ad-1478557658967-14').\n" +
                        "\tdefineSizeMapping(mapLeader).\n" +
                        "\taddService(googletag.pubads()); window.LeaderSlot = googletag.defineSlot('/2610296/mctdc-fk-calendarofevents-3', [[320, 50], [728, 90]], 'div-gpt-ad-1478557658967-15').\n" +
                        "\tdefineSizeMapping(mapLeader).\n" +
                        "\taddService(googletag.pubads());\n" +
                        "googletag.pubads().collapseEmptyDivs(true);\n" +
                        "googletag.enableServices();\n" +
                        "});\n" +
                        "\n" +
                        "</script>\n" +
                        "</head>\n" +
                        "<body id=\"top\" class=\"tdcus section section-florida-keys page-calendar unscrolled\">\n" +
                        "<!--[if lt IE 8]>\n" +
                        "\t\t\t<p class=\"browserupgrade\">You are using an <strong>outdated</strong> browser. Please <a href=\"http://browsehappy.com/\">upgrade your browser</a> to improve your experience.</p>\n" +
                        "\t\t<![endif]-->\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\" src=\"https://tag.yieldoptimizer.com/ps/ps?t=s&p=1382&pg=ot&sg=islamorada&sg=keywest&sg=keylargo&sg=marathon\"></script>\n" +
                        "\n" +
                        "<noscript><iframe src=\"https://www.googletagmanager.com/ns.html?id=GTM-KLKWT3\" height=\"0\" width=\"0\" style=\"display:none;visibility:hidden\"></iframe></noscript>\n" +
                        "\n" +
                        "<noscript><img height=\"1\" width=\"1\" src=\"https://www.facebook.com/tr?id=482626052232956&ev=PageView&noscript=1\" /></noscript>\n" +
                        "<script async src=\"https://www.googletagmanager.com/gtag/js?id=AW-982029061\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\">\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                        "  window.dataLayer = window.dataLayer || [];\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                        "  function gtag(){dataLayer.push(arguments);}\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                        "  gtag('js', new Date());\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                        "  gtag('config', 'AW-982029061');\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                        "</script>\n" +
                        "<script async src=\"https://www.googletagmanager.com/gtag/js?id=AW-776521984\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\">\n" +
                        "  window.dataLayer = window.dataLayer || [];\n" +
                        "  function gtag(){dataLayer.push(arguments);}\n" +
                        "  gtag('js', new Date());\n" +
                        "  gtag('config', 'AW-776521984');\n" +
                        "</script>\n" +
                        "<div id=\"wrapper\">\n" +
                        "<header>\n" +
                        "<h1><a href=\"/\">The Florida Keys & Key West</a></h1>\n" +
                        "<div class=\"mobile-icon\" id=\"mobile-nav\"><span class=\"fa fa-bars\"></span></div>\n" +
                        "<div class=\"mobile-icon\" id=\"mobile-search\"><span class=\"fa fa-search\"></span></div>\n" +
                        "<div class=\"dashboard dash-1\">\n" +
                        "<div class=\"dash-block clearfix\">\n" +
                        "<div class=\"dash-weather dash-item\">\n" +
                        "<iframe src=\"//fla-keys.com/temperature/florida-keys/\" scrolling=\"no\" frameborder=\"0\"></iframe>\n" +
                        "</div>\n" +
                        "<div class=\"dash-item dash-social tdc-social\">\n" +
                        "<ul class=\"clearfix\">\n" +
                        "<li><a href=\"https://www.facebook.com/floridakeysandkeywest/\" target=\"_blank\"><span class=\"fa fa-facebook-square\"></span><span class=\"icon-label\">Facebook</span></a></li>\n" +
                        "<li><a href=\"https://www.instagram.com/thefloridakeys/\" target=\"_blank\"><span class=\"fa fa-instagram\"></span><span class=\"icon-label\">Instagram</span></a></li>\n" +
                        "<li><a href=\"https://twitter.com/thefloridakeys\" target=\"_blank\"><span class=\"fa fa-twitter-square\"></span><span class=\"icon-label\">Twitter</span></a></li>\n" +
                        "<li><a href=\"https://www.youtube.com/user/FloridaKeysTV\" target=\"_blank\"><span class=\"fa fa-youtube-square\"></span><span class=\"icon-label\">YouTube</span></a></li>\n" +
                        "<li><a href=\"https://pinterest.com/flkeyskeywest\" target=\"_blank\"><span class=\"fa fa-pinterest-square\"></span><span class=\"icon-label\">Pinterest</span></a></li>\n" +
                        "<li>\n" +
                        "<div id=\"fb-root\"></div>\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\">(function(d, s, id) {\n" +
                        "  var js, fjs = d.getElementsByTagName(s)[0];\n" +
                        "  if (d.getElementById(id)) return;\n" +
                        "  js = d.createElement(s); js.id = id;\n" +
                        "  js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.5\";\n" +
                        "  fjs.parentNode.insertBefore(js, fjs);\n" +
                        "}(document, 'script', 'facebook-jssdk'));</script>\n" +
                        "<div class=\"fb-like\" data-href=\"/\" data-layout=\"button_count\" data-action=\"like\" data-show-faces=\"false\" data-share=\"false\"></div>\n" +
                        "</li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"dashboard dash-2\">\n" +
                        "<div id=\"tdc-header-newsletter\" class=\"dash-newsletter tdc-newsletter\"><link rel=\"stylesheet\" href=\"/css/site-promo.css\">\n" +
                        "<script type=\"9a55a0d385594b52fd861198-text/javascript\">\n" +
                        "$(document).ready(function() {\n" +
                        "\t$( \"#site-promo-header\" ).prependTo( \"#tdc-header-newsletter\" );\n" +
                        "});\n" +
                        "</script>\n" +
                        "<div id=\"site-promo-header\" class=\"site-promo-block\">\n" +
                        "<a href=\"/sustain/\"><img src=\"/img/section/florida-keys/sustain/sustain-header-promo.png\" alt=\"The Keys to Sustainable Travel\"></a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</header>\n" +
                        "<nav id=\"main-nav\" class=\"main clearfix\">\n" +
                        "<ul class=\"ui\"><li id=\"nav-destinations\" class=\"nav-hed\"><a href=\"/destinations/\"><span class=\"fa fa-flag\"></span> Our Destinations <span class=\"fa fa-angle-down\"></span></a>\n" +
                        "<ul>\n" +
                        "<li><a href=\"/key-west/\">Key West</a></li>\n" +
                        "<li><a href=\"/lower-keys/\">The Lower Keys</a></li>\n" +
                        "<li><a href=\"/marathon/\">Marathon</a></li>\n" +
                        "<li><a href=\"/islamorada/\">Islamorada</a></li>\n" +
                        "<li><a href=\"/key-largo/\">Key Largo</a></li>\n" +
                        "</ul>\n" +
                        "</li><li id=\"nav-famous\" class=\"nav-hed\"><a href=\"/famous/\"><span class=\"fa fa-trophy\"></span>We&#8217;re Famous For&#8230; <span class=\"fa fa-angle-down\"></span></a>\n" +
                        "<ul>\n" +
                        "<li><a href=\"/diving/\">Diving & Snorkeling</a></li>\n" +
                        "<li><a href=\"/fishing/\">Fishing</a></li>\n" +
                        "<li><a href=\"/arts-culture/\">Arts & Culture</a></li>\n" +
                        "<li><a href=\"/food-drink/\">Food & Drink</a></li>\n" +
                        "<li><a href=\"/weddings/\">Weddings</a></li>\n" +
                        "</ul>\n" +
                        "</li><li id=\"nav-plan\" class=\"nav-hed\"><a href=\"/plan/\"><span class=\"fa fa-suitcase\"></span> Plan Your Vacation <span class=\"fa fa-angle-down\"></span></a>\n" +
                        "<ul>\n" +
                        "<li><a href=\"/places-to-stay/\">Places to Stay</a></li>\n" +
                        "<li><a href=\"/things-to-do/\">Things to Do</a></li>\n" +
                        "<li><a href=\"/calendar/\">Calendar of Events</a></li>\n" +
                        "<li><a href=\"/how-to-get-here/\">How to Get Here</a></li>\n" +
                        "<li><a href=\"/boating/\">Boating & On the Water</a></li>\n" +
                        "<li><a href=\"/sustain/\">Sustainable Travel</a></li>\n" +
                        "<li><a href=\"/family-travel/\">Families of All Ages</a></li>\n" +
                        "<li><a href=\"/pet-friendly/\">Pet-Friendly Travel</a></li>\n" +
                        "<li><a href=\"/key-west/lgbtq/\">LGBTQ Info</a></li>\n" +
                        "<li><a href=\"/visitor-safety/\">Visitor Safety</a></li>\n" +
                        "<li><a href=\"/travelers-with-disabilities/\">Travelers with Disabilities</a></li>\n" +
                        "<li><a href=\"/maps/\">Maps</a></li>\n" +
                        "<li><a href=\"/weather/\">Weather</a></li>\n" +
                        "</ul>\n" +
                        "</li><li id=\"nav-know\" class=\"nav-hed\"><a href=\"/know/\"><span class=\"fa fa-heart\"></span> Get to Know Us <span class=\"fa fa-angle-down\"></span></a>\n" +
                        "<ul>\n" +
                        "<li><a href=\"/news/\">Keys News</a></li>\n" +
                        "<li><a href=\"/keysvoices/\" target=\"_blank\">Keys Voices Blog<span class=\"fa fa-fw fa-external-link-square\"></span></a></li>\n" +
                        "<li><a href=\"/keys-traveler/\">Keys Traveler E-Newsletter</a></li>\n" +
                        "<li><a href=\"/brochures-newsletters/\">Brochures & Newsletters</a></li>\n" +
                        "<li><a href=\"/locals-choice/\">Locals Choice Vacation Guide</a></li>\n" +
                        "<li><a href=\"/keys-crafted/\">Crafted in the Florida Keys</a></li>\n" +
                        "<li><a href=\"/movies-tv/\">The Keys in Movies and on TV</a></li>\n" +
                        "</ul>\n" +
                        "</li><li id=\"nav-webcams\" class=\"nav-hed\"><a href=\"/webcams-videos/\"><span class=\"fa fa-video-camera\"></span> Webcams & Videos <span class=\"fa fa-angle-down\"></span></a>\n" +
                        "<ul>\n" +
                        "<li><a href=\"/webcams/\">Florida Keys Webcams</a></li>\n" +
                        "<li><a href=\"/webcams/water-beach-views/\">Water & Beach Views</a></li>\n" +
                        "<li><a href=\"/webcams/key-west/\">Key West Webcams</a></li>\n" +
                        "<li><a href=\"/webcams/lower-keys/\">Lower Keys Webcams</a></li>\n" +
                        "<li><a href=\"/webcams/marathon/\">Marathon Webcams</a></li>\n" +
                        "<li><a href=\"/webcams/islamorada/\">Islamorada Webcams</a></li>\n" +
                        "<li><a href=\"/webcams/key-largo/\">Key Largo Webcams</a></li>\n" +
                        "<li><a href=\"/videos/\">Keys Videos</a></li>\n" +
                        "<li><a href=\"https://www.youtube.com/user/FloridaKeysTV\">FloridaKeysTV YouTube Channel<span class=\"fa fa-fw fa-external-link-square\"></span></a></li>\n" +
                        "</ul>\n" +
                        "</li><li id=\"nav-connect\" class=\"nav-hed\"><a href=\"/connect/\"><span class=\"fa fa-thumbs-up\"></span> Connect With Us <span class=\"fa fa-angle-down\"></span></a>\n" +
                        "<ul>\n" +
                        "<li><a href=\"/photoadventure/\">Share your Keys pictures<span class=\"fa fa-fw fa-external-link-square\"></span></a></li>\n" +
                        "<li><a href=\"/cdn-cgi/l/email-protection#e295878087868b968d90a2848e83cf89879b91cc818d8fdd91978088878196dfb3978791968b8d8c\">E-mail us a question <span class=\"fa fa-fw fa-paper-plane\"></span></a></li>\n" +
                        "<li><a href=\"http://twooceansdigital.com/destinationpricing/\" target=\"_blank\">Advertise on our site<span class=\"fa fa-fw fa-external-link-square\"></span></a></li>\n" +
                        "<li><a href=\"http://media.fla-keys.com/\" target=\"_blank\">For Media/Press<span class=\"fa fa-fw fa-external-link-square\"></span></a></li>\n" +
                        "<li><a href=\"http://media.fla-keys.com/login.cfm?path_info=/press-kit/lgbt/\" target=\"_blank\">For LGBTQ Media<span class=\"fa fa-fw fa-external-link-square\"></span></a></li>\n" +
                        "<li><a href=\"/meeting-planners/\">For Meeting Planners</a></li>\n" +
                        "<li><a href=\"/travel-agents-tour-operators/\">For Travel Agents/Tour Operators</a></li>\n" +
                        "<li><a href=\"/international-travelers/\">For International Travelers</a></li>\n" +
                        "</ul>\n" +
                        "</li><li id=\"nav-map\" class=\"nav-icon\"><a href=\"/map/\"><span class=\"fa fa-map-marker\"></span> Map</a></li><li id=\"nav-search\" class=\"nav-icon\"><a href=\"/search/\"><span class=\"fa fa-search\"></span> Search</a></li><li id=\"nav-home\"><a href=\"/\"><span class=\"fa fa-home\"></span> Home</a></li></ul>\n" +
                        "</nav>\n" +
                        "<div id=\"florida-keys-content\" class=\"content\">\n" +
                        "<div class=\"content-row clearfix\" id=\"floridakeys-calendar\">\n" +
                        "<div class=\"content-col content-one\">\n" +
                        "<article class=\"page\">\n" +
                        "<h1><a href=\"/calendar/\">Calendar of Events</a></h1>\n" +
                        "<div id=\"banner-display-1\" class=\"banner-block banner-horiz\"><div id=\"div-gpt-ad-1478557658967-13\"><script data-cfasync=\"false\" src=\"/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js\"></script><script type=\"9a55a0d385594b52fd861198-text/javascript\">googletag.cmd.push(function() { googletag.display(\"div-gpt-ad-1478557658967-13\"); });</script></div></div>\n" +
                        "<div class=\"calendar-filter\">\n" +
                        "<form action=\"/calendar/\" method=\"get\">\n" +
                        "<div>\n" +
                        "<label for=\"calendar-date\"><span class=\"fa fa-fw fa-calendar\"></span> Event date</label>\n" +
                        "<select id=\"calendar-date\" name=\"yearmonth\">\n" +
                        "<option value=\"current\">Select date</option>\n" +
                        "<option value=\"current\">Current Events</option>\n" +
                        "<option value=\"201911\">November 2019</option>\n" +
                        "<option value=\"201912\">December 2019</option>\n" +
                        "<option value=\"202001\">January 2020</option>\n" +
                        "<option value=\"202002\">February 2020</option>\n" +
                        "<option value=\"202003\">March 2020</option>\n" +
                        "<option value=\"202004\">April 2020</option>\n" +
                        "<option value=\"202005\">May 2020</option>\n" +
                        "<option value=\"202006\" selected>June 2020</option>\n" +
                        "<option value=\"202007\">July 2020</option>\n" +
                        "<option value=\"202008\">August 2020</option>\n" +
                        "<option value=\"202009\">September 2020</option>\n" +
                        "<option value=\"202010\">October 2020</option>\n" +
                        "<option value=\"past-events\">----------</option>\n" +
                        "<option value=\"past-events\">Past Events</option>\n" +
                        "</select>\n" +
                        "</div>\n" +
                        "<div>\n" +
                        "<label for=\"calendar-location\"><span class=\"fa fa-fw fa-map-marker\"></span> Event location</label>\n" +
                        "<select id=\"calendar-location\" name=\"section\">\n" +
                        "<option>Select location</option>\n" +
                        "<option value=\"florida-keys\" selected>All Florida Keys</option>\n" +
                        "<option value=\"key-west\">Key West</option>\n" +
                        "<option value=\"lower-keys\">The Lower Keys</option>\n" +
                        "<option value=\"marathon\">Marathon</option>\n" +
                        "<option value=\"islamorada\">Islamorada</option>\n" +
                        "<option value=\"key-largo\">Key Largo</option>\n" +
                        "</select>\n" +
                        "</div>\n" +
                        "<div>\n" +
                        "<label for=\"calendar-category\"><span class=\"fa fa-fw fa-tags\"></span> Event category</label>\n" +
                        "<select id=\"calendar-category\" name=\"category\">\n" +
                        "<option>Select category</option>\n" +
                        "<option value=\"all\" selected>All events</option>\n" +
                        "<option value=\"fishing\">Fishing events</option>\n" +
                        "<option value=\"arts-culture\">Arts & culture events</option>\n" +
                        "</select>\n" +
                        "</div>\n" +
                        "<div>\n" +
                        "<label for=\"calendar-submit\"><div style=\"text-indent: -9999px;\">Filter events</div></label>\n" +
                        "<input id=\"calendar-submit\" type=\"submit\" value=\"Filter events\">\n" +
                        "</div>\n" +
                        "</form>\n" +
                        "</div>\n" +
                        "<h2>June 2020: Florida Keys Events</h2>\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-4955\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 1, 2020 - Jun 5, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/islamorada/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Islamorada</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/fishing/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Fishing</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"https://guidestrustfoundation.org/\" target=\"_blank\" title=\"View website\">46th Annual Don Hawley Tarpon Fly Tournament</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-website\"><a href=\"https://guidestrustfoundation.org/\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">Up to 25 of the world’s top fly-rod anglers endure a five-day test of patience and finesse, fishing Keys waters using fly tackle and 12-pound tippet. Named for the late fly fisherman and conservationist Don Hawley, the tournament benefits the Guides Trust Foundation, assisting professional fishing guides and supporting backcountry fishery conservation programs. </li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar listing-calendar-img\" id=\"calendar-4905\">\n" +
                        "<div class=\"listing-img\">\n" +
                        "<div class=\"calendar-photos\" id=\"calendar-photos-4905\">\n" +
                        "<a class=\"swipebox expand-img\" href=\"/calendarofevents/img/4905-ev.jpg\"><span class=\"fa fa-expand\"></span><img src=\"/calendarofevents/img/4905-ev.jpg\" alt=\"Image for Fringe Theater presents: Five Lesbians Eating a Quiche\"></a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 2, 2020 - Jun 6, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/key-west/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Key West</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/arts-culture/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Arts & Culture</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"https://www.fringetheater.org/five-lesbians\" target=\"_blank\" title=\"View website\">Fringe Theater presents: Five Lesbians Eating a Quiche</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-phone\"><span class=\"fa fa-fw fa-phone\"></span>305-731-0581</span>\n" +
                        "<span class=\"listing-email\"><a href=\"/cdn-cgi/l/email-protection#7a13141c153a1c0813141d1f0e121f1b0e1f085415081d\"><span class=\"fa fa-fw fa-paper-plane\"></span>Email</a></span>\n" +
                        "<span class=\"listing-website\"><a href=\"https://www.fringetheater.org/five-lesbians\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">It’s 1956, and the Susan B. Anthony Society for the Sisters of Gertrude Stein are meeting for their annual quiche breakfast. Will they be able to keep their cool when a Communist attack threatens their idyllic way of life? Performance 7 p.m. at Key West Armory, 600 white St. Fringe Theater is Key West's community-focused theater providing unique opportunities for people to see and do theater. </li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar listing-calendar-img\" id=\"calendar-1973\">\n" +
                        "<div class=\"listing-img\">\n" +
                        "<div class=\"calendar-photos\" id=\"calendar-photos-1973\">\n" +
                        "<a class=\"swipebox expand-img\" href=\"/calendarofevents/img/PridefestFlag_2012.jpg\"><span class=\"fa fa-expand\"></span><img src=\"/calendarofevents/img/PridefestFlag_2012.jpg\" alt=\"Image for Key West Pride\"></a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 3, 2020 - Jun 7, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/key-west/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Key West</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"http://www.keywestpride.org\" target=\"_blank\" title=\"View website\">Key West Pride</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-phone\"><span class=\"fa fa-fw fa-phone\"></span>Matt Hon 305-294-4603</span>\n" +
                        "<span class=\"listing-email\"><a href=\"/cdn-cgi/l/email-protection#4e6e2b382b203a3d0e292f37252b37392b3d3a2822602d2123\"><span class=\"fa fa-fw fa-paper-plane\"></span>Email</a></span>\n" +
                        "<span class=\"listing-website\"><a href=\"http://www.keywestpride.org\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">Key West shows its Pride every day, and this is your chance to be a part of the celebration! The five-day schedule includes daytime pool and beach parties, late-night drag shows, on-the-water adventures ranging from snorkeling and kayaking to glass-bottom boat tours, a street fair, and pageants to select Mr., Miss and Ms. Key West Pride. </li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "<div id=\"banner-display-2\" class=\"banner-block banner-horiz\"><div id=\"div-gpt-ad-1478557658967-14\"><script data-cfasync=\"false\" src=\"/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js\"></script><script type=\"9a55a0d385594b52fd861198-text/javascript\">googletag.cmd.push(function() { googletag.display(\"div-gpt-ad-1478557658967-14\"); });</script></div></div>\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-3482\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 5, 2020 - Jun 7, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/lower-keys/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: The Lower Keys</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/fishing/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Fishing</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"http://lowerkeyschamber.com/tournaments.php\" target=\"_blank\" title=\"View website\">Original Big Pine & Lower Keys Dolphin Tournament</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-phone\"><span class=\"fa fa-fw fa-phone\"></span>800-872-2411 </span>\n" +
                        "<span class=\"listing-email\"><a href=\"/cdn-cgi/l/email-protection#89ecf1eceafcfde0ffecede0fbeceafde6fbc9e5e6feecfbe2ecf0faeae1e8e4ebecfba7eae6e4\"><span class=\"fa fa-fw fa-paper-plane\"></span>Email</a></span>\n" +
                        "<span class=\"listing-website\"><a href=\"http://lowerkeyschamber.com/tournaments.php\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">The Lower Keys Chamber of Commerce presents its 26th annual event, where anglers contend for more than $35,000 in cash prizes and awards. A special cash prize of $20,000 is awarded for the largest dolphin over 50 pounds. Additional prizes await winners in the open, ladies and youth divisions. </li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-4956\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 9, 2020 - Jun 11, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/islamorada/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Islamorada</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/fishing/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Fishing</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"https://www.facebook.com/keystarpon\" target=\"_blank\" title=\"View website\">Ladies Tarpon Fly Tournament</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-website\"><a href=\"https://www.facebook.com/keystarpon\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">An all-release invitational tournament, this event represents a rich history of premier lady fly anglers who love the sport of saltwater fishing in the shallow waters of Islamorada and Florida Bay. </li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-4957\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 12, 2020 - Jun 13, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/islamorada/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Islamorada</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/fishing/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Fishing</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\">Seventh Annual Ladies Dolphin Tournament</li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-phone\"><span class=\"fa fa-fw fa-phone\"></span>Dianne Harbaugh 305-522-4868</span>\n" +
                        "<span class=\"listing-email\"><a href=\"/cdn-cgi/l/email-protection#15717c617a60677b7478707b616655747a793b767a78\"><span class=\"fa fa-fw fa-paper-plane\"></span>Email</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">Hosted by the Florida Keys Elks Lodge, this event welcomes female angler teams to compete for the highest aggregate weight of three dolphin fish. Cash and prizes are awarded to first- through third-place finishers, and for the largest dolphin caught. </li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "<div id=\"banner-display-3\" class=\"banner-block banner-horiz\"><div id=\"div-gpt-ad-1478557658967-15\"><script data-cfasync=\"false\" src=\"/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js\"></script><script type=\"9a55a0d385594b52fd861198-text/javascript\">googletag.cmd.push(function() { googletag.display(\"div-gpt-ad-1478557658967-15\"); });</script></div></div>\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-4184\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 12, 2020 - Jun 14, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/islamorada/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Islamorada</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/fishing/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Fishing</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"http://www.ladiesletsgofishing.com\" target=\"_blank\" title=\"View website\">LLGF Screamin' Reels Tournament</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-phone\"><span class=\"fa fa-fw fa-phone\"></span>Betty Bauman 954-475-9068</span>\n" +
                        "<span class=\"listing-email\"><a href=\"/cdn-cgi/l/email-protection#7e171018113e121f1a171b0d121b0a0d191118170d16171019501d1113\"><span class=\"fa fa-fw fa-paper-plane\"></span>Email</a></span>\n" +
                        "<span class=\"listing-website\"><a href=\"http://www.ladiesletsgofishing.com\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">Intended for novice anglers, enjoy a weekend of learning and a non-intimidating, fun competition with the Ladies Let's Go Fishing university organizers. Species include legal inshore and offshore fish. Choose an optional charter experience or fish on your own boat. This is a casual tournament with prizes for heaviest offshore fish, longest inshore fish and drawings for prizes of those who caught fish, plus bonus drawings.</li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-4958\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 15, 2020 - Jun 19, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/islamorada/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Islamorada</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/fishing/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Fishing</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"https://goldcuptt.com/\" target=\"_blank\" title=\"View website\">57th Annual Gold Cup Tarpon Tournament</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-website\"><a href=\"https://goldcuptt.com/\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">This annual challenge appeals to “tarpon addicts” and both experienced and novice anglers apply to compete in what is referred to locally as the “Wimbledon of tarpon fishing.” Headquartered at the Lorelei Restaurant and Marina, the all-release event is limited to 25 anglers by invitation only. Partial proceeds benefit the Guides Trust Foundation of the Florida Keys. </li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar listing-calendar-img\" id=\"calendar-2068\">\n" +
                        "<div class=\"listing-img\">\n" +
                        "<div class=\"calendar-photos\" id=\"calendar-photos-2068\">\n" +
                        "<a class=\"swipebox expand-img\" href=\"/calendarofevents/img/Swim_KW_2012_2 web.jpg\"><span class=\"fa fa-expand\"></span><img src=\"/calendarofevents/img/Swim_KW_2012_2 web.jpg\" alt=\"Image for FKCC Swim Around Key West \"></a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 20, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/key-west/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Key West</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/arts-culture/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Arts & Culture</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"http://www.fkccswimaroundkeywest.com/\" target=\"_blank\" title=\"View website\">FKCC Swim Around Key West </a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-phone\"><span class=\"fa fa-fw fa-phone\"></span>Lori Bosco 305-809-3562</span>\n" +
                        "<span class=\"listing-email\"><a href=\"/cdn-cgi/l/email-protection#43223236222f2103222c2f6d202c2e78632f2c312a6d212c30202c03252820206d262736\"><span class=\"fa fa-fw fa-paper-plane\"></span>Email</a></span>\n" +
                        "<span class=\"listing-website\"><a href=\"http://www.fkccswimaroundkeywest.com/\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">The officially sanctioned event is a 12.5-mile swim clockwise around the island of Key West that is open to all age groups. Individual swimmers and relay teams can compete. The route takes swimmers through the waters of the Atlantic Ocean and Gulf of Mexico, ending where they began at Higgs Beach.\n" +
                        "</li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-4959\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 24, 2020 - Jun 25, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/islamorada/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Islamorada</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/fishing/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Fishing</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"http://theislamoradafishingclub.com/\" target=\"_blank\" title=\"View website\">IFC Captains Cup Dolphin Tournament</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-website\"><a href=\"http://theislamoradafishingclub.com/\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">Teams of up to four anglers compete for top prizes, and the total combined weight of three fish determines the winning team. If up to 25 boats register, first-prize cash winnings can reach $25,000.</li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar\" id=\"calendar-4770\">\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 25, 2020 - Jun 28, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/key-west/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Key West</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"https://mangofestkeywest.com/\" target=\"_blank\" title=\"View website\">Mango Fest Key West 2020</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-website\"><a href=\"https://mangofestkeywest.com/\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">Mango Fest of Key West features all things mango including mango tasting, mango trees and mango daiquiris. The culinary competitions between local chefs and residents alike showcases the colorful abilities of what the mango, also known as the king of fruit, has to offer. The Vendor Village gives art collectors and foodies the experience of a festival atmosphere. Music is provided along with live radio broadcasts. From 10AM to 3PM, Bayview Park, 1400 Truman Ave.</li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"listing-block listing-calendar listing-calendar-img\" id=\"calendar-4680\">\n" +
                        "<div class=\"listing-img\">\n" +
                        "<div class=\"calendar-photos\" id=\"calendar-photos-4680\">\n" +
                        "<a class=\"swipebox expand-img\" href=\"/calendarofevents/img/4680-ev.jpg\"><span class=\"fa fa-expand\"></span><img src=\"/calendarofevents/img/4680-ev.jpg\" alt=\"Image for Mystery Fest Key West\"></a>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<ul class=\"ui\">\n" +
                        "<li class=\"listing-info\">\n" +
                        "<span class=\"listing-date\"><span class=\"fa fa-fw fa-calendar\"></span>Jun 26, 2020 - Jun 28, 2020</span>\n" +
                        "<span class=\"listing-location\"><a href=\"/calendar/key-west/\"><span class=\"fa fa-fw fa-map-marker\"></span>Location: Key West</a></span>\n" +
                        "<span class=\"listing-type\"><a href=\"/calendar/arts-culture/\"><span class=\"fa fa-fw fa-tags\"></span>Category: Arts & Culture</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-name\"><a rel=\"nofollow\" href=\"http://mysteryfestkeywest.com/\" target=\"_blank\" title=\"View website\">Mystery Fest Key West</a></li>\n" +
                        "<li class=\"listing-contact\">\n" +
                        "<span class=\"listing-website\"><a href=\"http://mysteryfestkeywest.com/\" target=\"_blank\"><span class=\"fa fa-fw fa-external-link\"></span>Website</a></span>\n" +
                        "</li>\n" +
                        "<li class=\"listing-desc\">Renowned mystery writers, aspiring authors, true crime experts and fans meet in America’s southernmost city. The Fest features a who’s-who of award-winning and bestselling mystery/suspense authors and true crime experts, Mystery Fest Key West is a fun and fascinating meet-and-greet where writers can catch up with old friends and readers can meet leading authors, collect signed books and participate in workshops, panels, and presentations where the most devious of minds explain why—and how—they do it.</li>\n" +
                        "</ul>\n" +
                        "</div> \n" +
                        "\n" +
                        "<div class=\"news-posted news-return\">\n" +
                        "<a href=\"/calendar/\">All events</a>\n" +
                        "</div>\n" +
                        "</article>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<footer class=\"clearfix\">\n" +
                        "<div class=\"footer-block\" id=\"tdc-footer-backtotop\">\n" +
                        "<p class=\"back-to-top\"><a class=\"scroller\" href=\"#top\"><span class=\"fa fa-arrow-circle-up\"></span><span class=\"icon-label\">Back to the top</span></a></p>\n" +
                        "</div>\n" +
                        "<div class=\"footer-block\" id=\"tdc-footer-newsletter\"></div>\n" +
                        "<div class=\"footer-block\" id=\"tdc-footer-social\">\n" +
                        "<div class=\"tdc-social\">\n" +
                        "<p>Join us on social media</p>\n" +
                        "<ul class=\"clearfix\">\n" +
                        "<li><a href=\"https://www.facebook.com/floridakeysandkeywest/app/208195102528120/\" target=\"_blank\"><span class=\"fa fa-facebook-square\"></span><span class=\"icon-label\">Facebook</span></a></li>\n" +
                        "<li><a href=\"https://www.instagram.com/thefloridakeys/\" target=\"_blank\"><span class=\"fa fa-instagram\"></span><span class=\"icon-label\">Instagram</span></a></li>\n" +
                        "<li><a href=\"https://twitter.com/thefloridakeys\" target=\"_blank\"><span class=\"fa fa-twitter-square\"></span><span class=\"icon-label\">Twitter</span></a></li>\n" +
                        "<li><a href=\"https://www.youtube.com/user/FloridaKeysTV\" target=\"_blank\"><span class=\"fa fa-youtube-square\"></span><span class=\"icon-label\">YouTube</span></a></li>\n" +
                        "<li><a href=\"https://pinterest.com/flkeyskeywest\" target=\"_blank\"><span class=\"fa fa-pinterest-square\"></span><span class=\"icon-label\">Pinterest</span></a></li>\n" +
                        "<li><a href=\"https://plus.google.com/112681987952382863710\" target=\"_blank\"><span class=\"fa fa-google-plus-square\"></span><span class=\"icon-label\">Google+</span></a></li>\n" +
                        "</ul>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"footer-block\" id=\"tdc-footer-translate\">\n" +
                        "<div class=\"tdc-translate\">\n" +
                        "<div id=\"google_translate_element\"></div>\n" +
                        "<script data-cfasync=\"false\" src=\"/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js\"></script><script type=\"9a55a0d385594b52fd861198-text/javascript\">\n" +
                        "function googleTranslateElementInit() {\n" +
                        "\tnew google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');\n" +
                        "}\n" +
                        "</script>\n" +
                        "<script src=\"//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"footer-block\" id=\"tdc-footer-share\">\n" +
                        "<div class=\"tdc-share\">\n" +
                        "<script src=\"//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-57a10cbbc24371e4\" type=\"9a55a0d385594b52fd861198-text/javascript\"></script>\n" +
                        "<p><a class=\"addthis_button\" href=\"https://www.addthis.com/bookmark.php?v=300&amp;pubid=ra-4e152c2f4343bb40\"><span class=\"fa fa-share\"></span> Share</a></p>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"footer-block\" id=\"tdc-footer-contact\">\n" +
                        "<div class=\"tdc-links\">\n" +
                        "<ul>\n" +
                        "<li><a href=\"tel:18003525397\"><span class=\"fa fa-phone-square\"></span> Call us toll-free: 1-800-FLA-KEYS</a></li>\n" +
                        "<li><a href=\"/cdn-cgi/l/email-protection#b7c0d2d5d2d3dec3d8c5f7d1dbd69adcd2cec499d4d8da88c4c2d5ddd2d4c38ae6c2d2c4c3ded8d9\"><span class=\"fa fa-paper-plane\"></span> E-mail us a question</a></li>\n" +
                        "<li><a href=\"http://twooceansdigital.com/destinationpricing/\" target=\"_blank\"><span class=\"fa fa-bullhorn\"></span> Advertise on our site</a></li>\n" +
                        "</ul>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"footer-block\" id=\"tdc-footer-credits\">\n" +
                        "<div class=\"tdc-links\">\n" +
                        "<ul>\n" +
                        "<li><a href=\"/\"><span class=\"fa fa-copyright\"></span> Monroe County Tourist Development Council</a></li>\n" +
                        "<li><a href=\"http://media.fla-keys.com/\" target=\"_blank\">Media</a> | <a href=\"/site-map/\">Site map</a> | <a href=\"/privacy-policy/\">Privacy policy</a></li>\n" +
                        "<li><a href=\"http://twooceansdigital.com/\" target=\"_blank\">Digital marketing by Two Oceans Digital</a></li>\n" +
                        "</ul>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</footer>\n" +
                        "</div> \n" +
                        "<div id=\"map-screen\" class=\"full-screen-block\">\n" +
                        "<div class=\"full-screen\">\n" +
                        "<a class=\"full-screen-close\" href=\"#\"><span class=\"fa fa-close\"></span><span class=\"icon-label\">Close</span></a>\n" +
                        "<div class=\"full-screen-map floridakeys\"></div>\n" +
                        "<div class=\"keywest\"></div>\n" +
                        "<div class=\"lowerkeys\"></div>\n" +
                        "<div class=\"marathon\"></div>\n" +
                        "<div class=\"islamorada\"></div>\n" +
                        "<div class=\"keylargo\"></div>\n" +
                        "<ul id=\"on-the-map-nav\">\n" +
                        "<li id=\"on-the-map-keywest\"><a href=\"/key-west/\"><span>Key West</span></a></li>\n" +
                        "<li id=\"on-the-map-lowerkeys\"><a href=\"/lower-keys/\"><span>The Lower Keys</span></a></li>\n" +
                        "<li id=\"on-the-map-marathon\"><a href=\"/marathon/\"><span>Marathon</span></a></li>\n" +
                        "<li id=\"on-the-map-islamorada\"><a href=\"/islamorada/\"><span>Islamorada</span></a></li>\n" +
                        "<li id=\"on-the-map-keylargo\"><a href=\"/key-largo/\"><span>Key Largo</span></a></li>\n" +
                        "</ul>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div id=\"search-screen\" class=\"full-screen-block\">\n" +
                        "<div class=\"full-screen\">\n" +
                        "<a class=\"full-screen-close\" href=\"#\"><span class=\"fa fa-close\"></span><span class=\"icon-label\">Close</span></a>\n" +
                        "<div class=\"full-screen-search\">\n" +
                        "<h1><label for=\"tdc-site-search\">Search our site</label></h1>\n" +
                        "<form method=\"get\" action=\"/search/\">\n" +
                        "<input type=\"text\" name=\"q\" id=\"tdc-site-search\" placeholder=\"\" value=\"\">\n" +
                        "<input type=\"submit\" id=\"tdc-site-search-submit\" value=\"Submit\">\n" +
                        "</form>\n" +
                        "<h2>or</h2>\n" +
                        "<ul><li><a href=\"/places-to-stay/\"><span class=\"fa fa-bed\"></span> Find places to stay <span class=\"fa fa-angle-right\"></span></a></li><li><a href=\"/things-to-do/\"><span class=\"fa fa-ticket\"></span> Find things to do <span class=\"fa fa-angle-right\"></span></a></li><li><a href=\"/calendar/\"><span class=\"fa fa-calendar\"></span> Find upcoming events <span class=\"fa fa-angle-right\"></span></a></li><li><a href=\"/weddings/\"><span class=\"fa fa-diamond\"></span> Find wedding info <span class=\"fa fa-angle-right\"></span></a></li></ul>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div id=\"main-search\">\n" +
                        "<ul>\n" +
                        "<li class=\"search-field\">\n" +
                        "<label for=\"tdc-site-search-1\">Search our site</label>\n" +
                        "<form method=\"get\" action=\"/search/\">\n" +
                        "<span class=\"fa fa-search\"></span>\n" +
                        "<input type=\"text\" name=\"q\" id=\"tdc-site-search-1\" placeholder=\"\" value=\"\">\n" +
                        "<input type=\"submit\" name=\"submit\" id=\"tdc-site-search-submit-1\" value=\"Submit\">\n" +
                        "</form>\n" +
                        "</li>\n" +
                        " <li><a href=\"/places-to-stay/\"><span class=\"fa fa-bed\"></span> Find places to stay <span class=\"fa fa-angle-right\"></span></a></li>\n" +
                        "<li><a href=\"/things-to-do/\"><span class=\"fa fa-ticket\"></span> Find things to do <span class=\"fa fa-angle-right\"></span></a></li>\n" +
                        "<li><a href=\"/calendar/\"><span class=\"fa fa-calendar\"></span> Find upcoming events <span class=\"fa fa-angle-right\"></span></a></li>\n" +
                        "<li><a href=\"/weddings/\"><span class=\"fa fa-diamond\"></span> Find wedding info <span class=\"fa fa-angle-right\"></span></a></li>\n" +
                        "</ul>\n" +
                        "</div>\n" +
                        "<div id=\"tdc-newsletter-code\">\n" +
                        "<div class=\"tdc-newsletter\">\n" +
                        "<form id=\"ic_signupform\" captcha-key=\"6LeCZCcUAAAAALhxcQ5fN80W6Wa2K3GqRQK6WRjA\" captcha-theme=\"light\" new-captcha=\"true\" method=\"POST\" action=\"https://app.icontact.com/icp/core/mycontacts/signup/designer/form/?id=139&cid=566861&lid=10849\"><div class=\"elcontainer generous hidden-label left-aligned inline-button\">\n" +
                        "<div class=\"sortables\">\n" +
                        "<div class=\"formEl fieldtype-input required\" data-validation-type=\"1\" data-label=\"Email\" style=\"display: inline-block; width: 100%;\">\n" +
                        "<label for=\"signup-email\">\n" +
                        "<span class=\"signup-email-h\">Get our <a href=\"/keys-traveler/\">Keys Traveler</a> E-Newsletter</span>\n" +
                        "<span class=\"signup-email-f\">Sign up for our <a href=\"/keys-traveler/\">E-Newsletter</a></span>\n" +
                        "</label>\n" +
                        "<input id=\"signup-email\" type=\"text\" placeholder=\"Your e-mail\" name=\"data[email]\"><input id=\"newsletter-submit\" type=\"submit\" value=\"Subscribe\" class=\"btn btn-submit\">\n" +
                        "</div>\n" +
                        "<p class=\"signup-links\"><a href=\"/newsletter/\" target=\"_blank\">Read current issue</a> | <a href=\"/privacy-policy/\">View privacy policy</a></p>\n" +
                        "<div class=\"formEl fieldtype-checkbox required\" dataname=\"listGroups\" data-validation-type=\"1\" data-label=\"Lists\" style=\"display: none; width: 100%;\"><h3>Lists<span class=\"indicator required\">*</span></h3><div class=\"option-container\"><label class=\"checkbox\"><input type=\"checkbox\" alt=\"Lists\" name=\"data[listGroups][]\" value=\"15142\" checked=\"checked\">Florida Keys and Key West Travel Information</label></div></div><div class=\"submit-container\"></div></div><div class=\"hidden-container\"></div></div>\n" +
                        "</form>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<script data-cfasync=\"false\" src=\"/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js\"></script><script src=\"https://ajax.cloudflare.com/cdn-cgi/scripts/95c75768/cloudflare-static/rocket-loader.min.js\" data-cf-settings=\"9a55a0d385594b52fd861198-|49\" defer=\"\"></script></body>\n" +
                        "</html> ";

        rawHTMLDataTest.add(testHTML);
        List<EventItem> theEventListData = myExtractWebEventsUtil.convertRawHTMLToEventList(rawHTMLDataTest);
        theEventListData.forEach(log::info);

        assertEquals(12, theEventListData.size());

    }

}
