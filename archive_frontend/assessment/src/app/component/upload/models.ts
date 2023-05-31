export interface response {
  bundleId: string;
}

export interface archive {
  bundleId: string;
  name: string;
  title: string;
  comments: string;
  date: number;
  urls: string[];
}

export interface archiveBundle {
  title: string;
  date: number;
}
