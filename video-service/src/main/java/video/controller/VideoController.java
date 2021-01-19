package video.controller;

import net.xdclass.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import video.service.VideoService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/video/")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("find_by_id")
    public Object findById(int videoId, HttpServletRequest request) {
        Video video = videoService.findById(videoId);
        String ipAndPort = request.getServerName() + ":" + request.getServerPort(); //得到被请求端服务的ip和端口
        video.setServeInfo(ipAndPort);
        return video;
    }

    @PostMapping("save")
    public int save(@RequestBody Video video) {
        System.out.println(video.getTitle());
        return 1;
    }
}
