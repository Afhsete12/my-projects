import json
import random
import requests
from bs4 import BeautifulSoup

WEB_SITE_MOVIE = "https://www.themoviedb.org/movie"
WEB_SITE_LANG = "language=en-US"
WEB_SITE_POSTERS = "images/posters?image_language=en"
WEB_SITE_BACKDROPS = f"images/backdrops?{WEB_SITE_LANG}"


class Movie:
    title = ''
    releaseDate = ''
    trailerLink = 'https://www.youtube.com/watch?v='
    genres = []
    poster = ''
    backdrops = []
    reviewIds = []


def main():
    s = requests.Session()
    s.headers.update({
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/108.0.0.0 Safari/537.36'
    })

    list_of_movies = movieProvider(s)

    json_string = json.dumps([ob.__dict__ for ob in list_of_movies])

    f = open("data.json", "a")
    f.write(json_string)
    f.close()

    print(json_string)


def movieProvider(s):
    return [
        movie(s, "593643-the-menu"),
        movie(s, "414906-the-batman"),
        movie(s, "576845-last-night-in-soho"),
        movie(s, "361743-top-gun-maverick"),
        movie(s, "338762-bloodshot"),
        movie(s, "663712-terrifier-2"),
        movie(s, "399579-alita-battle-angel"),
        movie(s, "1586-secret-window"),
        movie(s, "22970-the-cabin-in-the-woods")
    ]


def getRandomPoster(session, path):
    url = f"{WEB_SITE_MOVIE}/{path}/{WEB_SITE_POSTERS}"
    resp = session.get(url)
    soup = BeautifulSoup(resp.text, "html.parser")
    posters = soup.findAll('img')
    list_of_posters = []
    for i, poster in enumerate(posters):
        if poster["src"].endswith('.jpg'):
            if i != 1:
                list_of_posters.append(str("https://www.themoviedb.org" + poster["src"]))

    return list_of_posters[random.randint(0, len(list_of_posters) - 1)]


def getAllBackdrops(session, path):
    url = f"{WEB_SITE_MOVIE}/{path}/{WEB_SITE_BACKDROPS}"
    resp = session.get(url)
    soup = BeautifulSoup(resp.text, "html.parser")
    backdrops = soup.findAll('img')
    list_of_backdrops = []
    for i, backdrop in enumerate(backdrops):
        if backdrop["src"].endswith('.jpg'):
            if i != 1 | len(list_of_backdrops) <= 8:
                # print(str("https://www.themoviedb.org" + backdrop["src"] + str(i)))
                list_of_backdrops.append(str("https://www.themoviedb.org" + backdrop["src"]))

    print(list_of_backdrops)

    return list_of_backdrops


def movie(session, path):
    url = f"{WEB_SITE_MOVIE}/{path}?{WEB_SITE_LANG}"
    resp = session.get(url)
    soup = BeautifulSoup(resp.text, "html.parser")
    v_title = soup.select_one("h2 > a").text

    v_release_date = soup.select_one("span.release").text.strip().replace("(ES)", "")

    all_genres = soup.select("span.genres")

    yt_id = [item['data-id'] for item in soup.find_all('a', attrs={"data-id": True})]

    genres = ""

    for genre in all_genres:
        genres = genre.text.strip().replace("\u00a0", "")

    separate_genres = genres.split(",")

    arr_genres = []

    for g in separate_genres:
        arr_genres.append(g)

    poster = getRandomPoster(session, path)
    backdrops = getAllBackdrops(session, path)

    m = Movie()
    m.title = v_title
    m.trailerLink = str(m.trailerLink + yt_id[0])
    m.releaseDate = v_release_date
    m.genres = arr_genres
    m.poster = poster
    m.backdrops = backdrops
    m.reviewIds = []

    print(m)

    return m


if __name__ == '__main__':
    main()
