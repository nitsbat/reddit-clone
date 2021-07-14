import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SubredditModel } from './subreddit-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubredditService {

  constructor(private http: HttpClient) { }

  getAllSubreddits(): Observable<any> {
    return this.http.get('http://localhost:8080/api/subreddit');
  }

  createSubreddit(subredditModel: SubredditModel): Observable<any> {
    return this.http.post('http://localhost:8080/api/subreddit',
      subredditModel);
  }
}
