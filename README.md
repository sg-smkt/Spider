# Spider

## Note to Run Project 
For the source code, we added external jar for CoreNLP library on top of maven. Hence 
for the project to run, there is a need to download the CoreNLP library and configure 
the build path within eclipse. 

The jar can be download here: https://stanfordnlp.github.io/CoreNLP/
2 folder needs to be downloaded; the CoreNLP 4.2.0 and English model jar 

## General Project Idea 
The problems that come with organising events are that the participants' opinions of the events are not easily known from the organisers' perspective. Obtaining feedback directly from participants is a great way to get a feel of the overall opinions. However, the sample size feedback may not reflect the population data, and without a clear data matrix, there is no clear record of how effective each event is.

Creating an online feedback form is one such solution. However, it may only work effectively for small scale events when the participants are easily managed. For large scale events, there is no incentive for participants to spend time to do this feedback. The overall expenses that come with providing an incentive are also high. Hence there is a need for another tool to solve this potential problem for event organisers, especially when it comes to large scale events.

One such solution in the market is the use of sentiment analysis. We can make use of this technology to crawl event-based data on social media. With the assistance of sentiment classification, there is also no need to process the data individually. The general opinion is summarized and easily understandable at first glance.

Hence to better understand and deconstruct this area of technology, we decided to pursue sentimental analysis as an additional feature for our project, on top of crawling data from social media.


### Reddit API 
Jraw: Java API wrapper for Reddit; [Link](https://javadoc.jitpack.io/com/github/mattbdean/JRAW/v1.1.0/javadoc/index.html)<br />
#### Creating a Reddit Application
Steps:
1. Login with your reddit acc at https://www.reddit.com/prefs/apps
2. Create another app..
3. Enter app name, purpose, description, about url and redirect url
4. Create app
#### Oauth2
Client Id and Secret can be found here after the creation of the app<br />
![image](https://user-images.githubusercontent.com/74295056/109486194-81cc7580-7abd-11eb-9966-bb99717531d2.png)

### Twitter API
Twitter4J - http://twitter4j.org/en/index.html

### Sentiment Analysis 
CoreNLP: Natural Language Processing; [Link](https://stanfordnlp.github.io/CoreNLP/index.html)

#### Configuration Before Use 

![Guide](https://user-images.githubusercontent.com/23652958/108338841-aac54e80-7211-11eb-8f5b-9890141cbc34.png)

Steps: 
1. Download Core NLP 4.2.0 (About 500MB)
2. Download the english model jar (About 600MB)
3. Move english model jar into Core NLP 4.2.0 folder in Step 1
4. Configure CLASSPATH using the command in the visual picture above. 
  Change "/path/to/" to where the downloaded CoreNLP folder is at
5. Create an "input.txt" file with sample text and test using the command above 

Note: Not configuing Step 4. properly with result in error shown below 
![Screenshot from 2021-02-18 17-49-28](https://user-images.githubusercontent.com/23652958/108338941-c7fa1d00-7211-11eb-8aa3-7ed26f7128fb.png)


6. Open Eclipse go to configure build path 
   Right Click on Project > Build Path > Configure Build Path 
7. Go to "Library" Tab 

![Screenshot from 2021-02-18 17-53-36](https://user-images.githubusercontent.com/23652958/108339414-5a9abc00-7212-11eb-918a-29b8e045dfac.png)

8. Click on "Add external jar"
9. Add all the jar file in the CoreNLP folder 

![Screenshot from 2021-02-18 17-56-37](https://user-images.githubusercontent.com/23652958/108339699-b6654500-7212-11eb-893e-577b3ff2beb2.png)
![Screenshot from 2021-02-18 17-56-46](https://user-images.githubusercontent.com/23652958/108339733-c11fda00-7212-11eb-8406-6322d67ded62.png)

10. Test with sample code in Prototype Branch (Branch -> Prototype -> SentimentalSample.java)
11. You should get the result as follow

![Screenshot from 2021-02-18 17-59-43](https://user-images.githubusercontent.com/23652958/108340035-1cea6300-7213-11eb-96e3-33e708262ce1.png)

