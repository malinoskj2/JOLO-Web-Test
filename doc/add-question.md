# Adding New Questions
When a new TestSubmission is created, all corresponding Questions are retrieved from the database. Each Question has 2 sets of coordinates and the corresponding correct angle number. These coordinates are used by the client to draw the 2 lines corresponding with each Question.

Each Question is in the database is composed of the following columns.
| Column         | Function                                      |
|----------------|-----------------------------------------------|
| questionid     | auto_increment ID of the question             |
| correct_angle1 | the correct answer for the first line         |
| correct_angle2 | the correct answer for the second line        |
| position       | value used for ordering questions             |
| line1endx      | x coordinate of the point where line 1 ends   |
| line1endy      | y coordinate of the point where line 1 ends   |
| line1startx    | x coordinate of the point where line 1 starts |
| line1starty    | y coordinate of the point where line 1 starts |
| line2endx      | x coordinate of the point where line 2 ends   |
| line2endy      | y coordinate of the point where line 2 ends   |
| line2startx    | x coordinate of the point where line 2 starts |
| line2starty    | y coordinate of the point where line 2 starts |

There are 2 ways one might go about creating coordinates for new questions. 

 1. If the question is based on physical media (such as trials printed on paper), the media can be scanned and encoded as a 613x797 image. Each scan will contain 2 lines. The coordinates of the pixels where each line starts and ends, along with the correct angle can be inserted into the database as a Question.
 2. If new lines not based on physical media, any coordinates within a 612x797 grid can be used to define the start and end points of each line.


