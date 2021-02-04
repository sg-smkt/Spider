import praw

r_id = ''
r_secret = ''
r_user = ''

reddit = praw.Reddit(client_id=r_id,
                     client_secret=r_secret,
                     user_agent=r_user
                     )

#url = "https://www.reddit.com/r/AbruptChaos/comments/k91q37/axolotls_suddenly_disliked_everything/"
#submission = reddit.submission(url=url)
submission = reddit.submission(id="k91q37")
submission.comments.replace_more(limit=None)

for top_level_comment in submission.comments:
    comment = top_level_comment.body
    if(comment != '[deleted]'):
        print(comment)
