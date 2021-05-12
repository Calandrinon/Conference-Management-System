import {Conference} from "./conference";
import {DeadlineType} from "./deadlineType";

export class Deadline{
  id: number
  conferenceId: number
  date: Date
  deadlineType: DeadlineType
}
