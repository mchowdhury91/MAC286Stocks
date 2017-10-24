# MAC286Stocks

The purpose of this project was to grab data for stocks and indices from Yahoo Finance and test the pattern "Reversal New Highs" against the historical data. 

BigMain contains the main file that executes everything. 

The class "Downloader" provides the tools and code to grab data from Yahoo Finance. 

DownloadAll just utilizes Downloader to grab 11 years (2006-01-01 to 2016-12-31) worth of data from Yahoo Finance for all the stocks and indices listed in stocks.txt and indices.txt. The data that "DownloadAll" grabs gets stored in the Data folder as SYMBOL.csv (where SYMBOL is the stock/index symbol).

BigMain runs the simulations against all .csv files in the Data folder and then outputs some statistics to the Stats folder.

There is still a lot of cleaning up to do on this project.
