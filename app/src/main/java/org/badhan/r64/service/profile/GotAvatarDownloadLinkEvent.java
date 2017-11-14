package org.badhan.r64.service.profile;

import android.net.Uri;

public class GotAvatarDownloadLinkEvent {
    public Uri downloadUrl;

    public GotAvatarDownloadLinkEvent(Uri downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
