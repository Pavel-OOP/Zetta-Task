# Zetta-Task

Pull the master branch

All of the Tasks are in the test folder

After pulling make sure to install all dependencies using Maven

The first test called TestAmazonProducts goes into Amazon website, searches for laptops and adds all the non-discounted ones into cart.
Because we are looking for elements that DON'T exist(e.g. the discounted prices) the test may be a bit slow due to the nature of finding
elements that are NOT displayed.

The second test is called TestAmazonCrawl which gets all the links from the Shop By Department dropdown menu, the test is relatively fast
on finding the needed elements, but it takes time to visit all URLs and check if their status is ok and then write it into a file, that
will appear in the 'tmp' directory that will be created.

The third test is called TestPostsAPI, its a very fast test because its API, it literally runs for less then a second. Although the results
are what I understood from the requirements.

Run the tests and enjoy, but please be patient! Some of the scripts take time to do their thing :)
