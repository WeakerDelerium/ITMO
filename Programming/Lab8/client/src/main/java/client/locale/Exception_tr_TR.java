package client.locale;

import java.util.ListResourceBundle;

public class Exception_tr_TR extends ListResourceBundle {
    private final Object[][] contents = {
            {"userNotFound", "Kullanıcı `%s` bulunamadı"},
            {"userExists", "Kullanıcı `%s` zaten var"},
            {"wrongPassword", "Yanlış şifre"},
            {"emptyUsername", "Kullanıcı adı boş olamaz"},
            {"passwordFormat", "Yanlış şifre formatı (min - 8 sembol)"},
            {"authorizationNotAvailable", "Yetkilendirme şu anda mevcut değil :("},
            {"userInfoLost", "Kullanıcı yetkilendirme verilerini kontrol ederken hata"},
            {"idCheck", "Kimlik sıfırdan büyük olmalı"},
            {"idFormat", "Kimlik tam sayı olmalı"},
            {"nameFormat", "Isim boş olamaz"},
            {"coordXCheck", "X koordinatı 304'ten büyük olamaz"},
            {"coordXFormat", "X koordinatı tam sayı olmalı"},
            {"coordYFormat", "Y koordinatı kesirli sayı olmalı"},
            {"dateFormat", "Yanlış tarih formatı"},
            {"fromXFormat", "Konumdan X koordinatı tam sayı olmalı"},
            {"fromYFormat", "Konumdan Y koordinatı tam sayı olmalı"},
            {"fromZFormat", "Konumdan Z koordinatı tam sayı olmalı"},
            {"fromNameFormat", "Konumdan isim boş olamaz"},
            {"toXFormat", "Konumdan X koordinatı kesirli sayı olmalı"},
            {"toYFormat", "Konumdan Y koordinatı kesirli sayı olmalı"},
            {"toNameCheck", "Konuma isim 675'ten büyük olamaz"},
            {"toNameFormat", "Konuma isim boş olamaz"},
            {"distanceCheck", "Mesafe 1'den büyük olmalı"},
            {"distanceFormat", "Mesafe tam sayı olmalı"},
            {"incompatibleMethod", "Yöntem bu komutla uyumsuz"},
            {"idNotExist", "Önerilen kimlik ile koleksiyon öğesi yok"},
            {"idNotYour", "Erişim reddedildi - bu sizin öğeniz değil"},
            {"invalidScript", "Geçersiz betik"},
            {"fileNotSelected", "Dosya seçilmedi"},
            {"scriptRecursion", "Betik yinelemesi"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
