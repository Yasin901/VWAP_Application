
## VWAP Aplication

### Created using Java 18 with Swing

The purpose of this application is to display a list of trades within a GUI and calculate the 
Volume Weighted Average Price of a stock, based on a csv file containing market trades(*src/main/resources/market_trades.csv*). 

The GUI displays a table that can be filtered for a specific Epic. The GUI contains the ability to calculate the VWAP in two 
different ways. Together these calculations can be saved to a csv file or a JSON file.  

The VWAP has been calculated in two different ways, based on all trades with a chosen Epic or based on all trades 
with a chosen Epic and Trade Type (e.g., Automatic, Uncrossing).

To run this application simply run the main method via your IDE.
