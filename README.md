# Spider

## Sample Code 
Sample is at Branches -> Prototype

## General Project Idea 
Use API to request event related data from Twitter & Reddit for possible sentimental analysis

## Libraries
We could enter the libraries we are using or researching here

### Reddit API 
Praw: Python API wrapper for Reddit; [Link](https://praw.readthedocs.io/en/latest/) 
Jraw: Java API wrapper for Reddit; [Link](https://javadoc.jitpack.io/com/github/mattbdean/JRAW/v1.1.0/javadoc/index.html)

### Twitter API
(Insert here)

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

