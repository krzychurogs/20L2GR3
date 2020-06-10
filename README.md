# 20L2GR3

# Opis Projektu
Aplikacja dla pracowników hotelu ma na celu usprawnienie i monitorowanie pracy wykonywanej przez pracowników 
firmy oraz zarządzaniu zasobami.

Posiada ona 4 typy użytkowników zwyczajnych (sprzątacz, kucharz, recepcjonista, menadżer) oraz administratora który może zarządzać kontami innych użytkowników. Każdy z nich posiada swoje dane logowania. Tworzenie nowych kont jest możliwe tylko i wyłącznie za pomocą konta administratora. 

Recepcjonista w swoim panelu posiada listę pokojów oraz klientów którzy są do nich zapisani. Może on również przypisywać do nich nowych gości. Użytkownik ten służy również jako pośrednik pomiędzy klientami, a osobami świadczącymi usługi. W przypadku gdy dany gość chciałbym zamówić posiłek lub dodatkowe sprzątanie pokoju, musi skontaktować się z recepcjonistą. Na podstawie tych usług oraz wybranego pokoju wystawiany jest rachunek dla klienta.

Kucharz w swoim panelu posiada listę „Zadań” do wykonania. W jego przypadku są to zamówione przez gości posiłki które uzupełnił w systemie recepcjonista. Po wykonaniu zadania użytkownik powinien zaznaczyć w systemie jego wykonanie. System wylicza czas w jakim posiłek został wykonany a wgląd do tych danych posiada manager. 

Sprzątacz również posiada listę zadań lecz w jego przypadku są to pokoje do wysprzątania. Mogą one być dodawane na zamówienie klienta lub po zwolnieniu pokoju przez poprzedniego klienta. Ten użytkownik posiada również możliwość uzupełnienia danych o barku. Każdy klient posiada w swoim pokoju takowy a za korzystanie z dostępnych tam produktów pobierana jest płatność. Po uzupełnieniu w systemie zakończenia sprzątania pokoju użytkownik może uzupełnić aktualny stan barku. 

Manager posiada wgląd do rachunków klientów, jak i również do statystyk związanych z zasobami hotelu. Każde wykonane zadanie trafia do niego co pozwala mu zarządzać biznesową stroną projektu. Może on również generować raport finansowy oraz zarobków hotelu.

Administrator może zarządzać kontami użytkowników. Może zarówno tworzyć, usuwać jak i edytować wszystkie konta znajdujące się w systemie. Może również tworzyć innych administratorów. 

# Analiza wymagań funkcjonalnych
## Recepcjonista
- moze zarezerwowac gosciowi hotelowemu pokoj
- moze dodac zadanie dla kucharza 
- moze dodac zadanie dla sprzatacza
- moze przegladnac liste aktualnych rezerwacji
- moze przegladnac liste wszystkich rezerwacji dla danego pokoju
## Kucharz
- moze wykonywac dania zadane przez recepcjoniste
- moze anulowac dania zadane przez recepcjoniste
## Sprzatacz
- moze wykonywac dania zadane przez recepcjoniste
- moze anulowac dania zadane przez recepcjoniste
- sprawdza stan barku i informuje o brakach w barku w skutek czego dodawane sa do rachunku
## Manager
- moze sprawdzic statystyki finansowe, jakie zyski przynosi poszczegolna usługa
- widzi calkowity przychód firmy
- moze wygenerowac statystyki do pliku pdf
- moze sprawdzic dokladne rachunki gosci hotelowych ile jaki gość wydał na co
- moze sprawdzic ile jaki pokoj był razy rezerwowany
## Admin
- moze tworzyc konta pracownikow
- moze usuwac/edytowac dane pracowników
- widzi liste wszystkich pracownikow

# Diagramy UML

## Diagram aktywnosci

![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/diagram_aktywnosci.JPG)

## Diagram przypadkow uzycia
![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/diagram_przypadkow_uzycia.JPG)

## Diagram Klas
![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/diagram_klas.JPG)

## Diagram Sekwencji
![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/diagram_sekwencji.png)

# Obsługa aplikacji
Domyślne dane logowania:
- Administrator: admin 12345
- Recepcjonista: recepcjonista 12345
- Manager: manager 12345
- Kucharz: kucharz 12345
- Sprzatacz: sprzatacz 12345

Po uruchomieniu aplikacji wyświetlone zostanie menu główne aplikacji.

## Obsługa aplikacji z konta recepcjonisty.

Recepcjonista w swoim głównym oknie widzi pokoje które znajdują się w hotelu. Po
rozwinięciu plusem z prawej strony, ukażą się aktualne rezerwacje dla danego pokoju. Po
zaznaczeniu „zajęte pokoje” wyświetlą nam się wszystkie rezerwacje w hotelu, które
będziemy mogli sortować. 

![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/Recepcjonista.JPG)

Po kliknięciu „zarezerwuj” ukaże nam się okno do rezerwacji. W polach do wybierania
daty, możemy wybrać przedział czasowy, w jakim dany gość chce zatrzymać się w hotelu.
Po uzupełnieniu dat pokaże nam się lista wolnych w tym terminie pokoi. Możemy
zatwierdzić rezerwację przyciskiem „wyślij”.

![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/Recepcjonista-rezerwajc.JPG)

Recepcjonista może również dodawać zadania dla kucharzy oraz sprzątaczy. Po kliknięciu
w „Dodaj zadanie” ukaże się okno dodawania zadania. Recepcjonista może wybrać
- użytkownika
- usługę
- pokój
- dodać krótki opis
Po zatwierdzeniu zadania ukaża się wybranemu użytkownikowi.

![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/Recepcjonista%20-%20rezerwacja%202.JPG)

## Obsługa aplikacji z konta kucharza.

![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/zadania.JPG)\

Kucharzowi wyświetlają się zadania stworzone przez recepcjonistę. Po wybraniu któregoś
z nich, może zatwierdzić wykonanie zadania lub je anulować. Po zatwierdzeniu zostanie
automatycznie, doliczone do rachunku klienta który znajduje się aktualnie w pokoju
zapisanym do zamówienia.

## Obsługa aplikacji z konta sprzątacza.

Sprzątacz działa na takiej samej zasadzie co kucharz ale po zamówieniu uzupełnia
produkty które zniknęły z barku. Są one wtedy doliczane do rachunku.


![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/Barek.jpg)\

## Obsługa aplikacji z konta administratora.
Administrator może tworzyć, edytować lub usuwać konta użytkowników.


![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/stw%C3%B3rz%20konto.jpg)\

## Obsługa aplikacji z konta managera.

Manager może przeglądać statystyki. Znajdują się tam wykres sprzedaży usług
hotelowych oraz najczęściej wybierane pokoje. Ten użytkownik ma możliwość
generowania pliku PDF z listą gości oraz listą użytkowników.

![alt text](https://github.com/krzychurogs/20L2GR3/blob/master/diagramy/%C5%82acznyprzych%C3%B3d.JPG)\

# Wykonał
- Krzysztof Rogowski
- Dawid Hubicki
- Piotr Kłos
- Damian Gaworowski

# Technologie
- JavaFx
- Hibernate
- MySQL
- CSS




