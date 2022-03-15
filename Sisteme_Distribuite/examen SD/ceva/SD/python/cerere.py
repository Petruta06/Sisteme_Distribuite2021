
import requests
r = requests.get('https://finnhub.io/api/v1/stock/price-target?symbol=NFLX&token=brmrda7rh5rcss140no0')
print(r.json())
