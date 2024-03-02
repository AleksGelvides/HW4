package org.example.hw4.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.hw4.exceptions.CommonHWServiceException;
import org.example.hw4.exceptions.StupidSecurityException;
import org.example.hw4.service.api.CommentService;
import org.example.hw4.service.api.NewsService;
import org.example.hw4.service.api.UserService;
import org.example.hw4.web.dto.request.comment.CommentChangeRequest;
import org.example.hw4.web.dto.request.news.NewsChangeRequest;
import org.example.hw4.web.dto.request.user.UserChangeRequest;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Objects;

@Aspect
@Component
@AllArgsConstructor
public class HwSecurityImpl {
    private final NewsService newsService;
    private final UserService userService;
    private final CommentService commentService;
    private final static String REQUIRED_HEADER = "user-id";

    @Before("@annotation(org.example.hw4.aop.HwSecurity)")
    public void checkUser(JoinPoint joinPoint) throws Exception {

        String requireHeaderData = ((HttpServletRequest) joinPoint.getArgs()[1]).getHeader(REQUIRED_HEADER);

        var authorId = StringUtils.isEmpty(requireHeaderData) ? null : Long.parseLong(requireHeaderData);

        if (Objects.isNull(authorId))
            throw new StupidSecurityException(MessageFormat.format("Не указан обязательный Http заголовок: {0}", REQUIRED_HEADER));

        var req = joinPoint.getArgs()[0];

        Boolean needCheckId = !joinPoint.getSignature().getName().equals("save");

        if (Objects.isNull(req))
            throw new StupidSecurityException("Не передано changeRequest");

        if (req instanceof NewsChangeRequest)
            handleReq((NewsChangeRequest) req, needCheckId, authorId);
        if (req instanceof UserChangeRequest)
            handleReq((UserChangeRequest) req, needCheckId, authorId);
        if (req instanceof CommentChangeRequest)
            handleReq((CommentChangeRequest) req, needCheckId, authorId);
        if (req instanceof Long) {
            String deleteMethod = MessageFormat.format("{0}.{1}",
                    joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
            switch (deleteMethod) {
                case "NewsController.delete" -> {
                    var news = newsService.findById((Long) req);
                    checkAuthorIdWithHeaderAuthorId(news.getAuthor().getId(), authorId);
                }
                case "CommentController.delete" -> {
                    var comment = commentService.findById((Long) req);
                    checkAuthorIdWithHeaderAuthorId(comment.getAuthor().getId(), authorId);
                }
                case "UserController.delete" -> {
                    var user = userService.findById((Long) req);
                    checkAuthorIdWithHeaderAuthorId(user.getId(), authorId);
                }
                default -> throw new CommonHWServiceException("Отправлены не распознанные данные. Обратитесь к документации API");
            }
        }
    }


    private void handleReq(NewsChangeRequest req, Boolean needCheckId, Long id) {
        if (needCheckId) {
            var oldNews = newsService.findById(req.getId());
            checkAuthorIdWithHeaderAuthorId(oldNews.getAuthor().getId(), id);
        }
        if (Objects.isNull(req.getAuthorId())) {
            req.setAuthorId(id);
        }
        checkAuthorIdWithHeaderAuthorId(req.getAuthorId(), id);
    }

    private void handleReq(UserChangeRequest req, Boolean needCheckId, Long id) {
        if (needCheckId) {
            var oldUser = userService.findById(req.getId());
            checkAuthorIdWithHeaderAuthorId(oldUser.getId(), id);
        }
        if (Objects.nonNull(req.getId())) {
            checkAuthorIdWithHeaderAuthorId(req.getId(), id);
        }
    }

    private void handleReq(CommentChangeRequest req, Boolean needCheckId, Long id) {
        if (needCheckId) {
            var oldComment = commentService.findById(req.getId());
            checkAuthorIdWithHeaderAuthorId(oldComment.getAuthor().getId(), id);
        }
        if (Objects.isNull(req.getAuthorId())) {
            req.setAuthorId(id);
        }
        checkAuthorIdWithHeaderAuthorId(req.getAuthorId(), id);
    }

    @SneakyThrows
    private void checkAuthorIdWithHeaderAuthorId(Long id, Long idFromHeader) {
        if (!Objects.equals(id, idFromHeader)) {
            throw new StupidSecurityException("Вы не можете взаимодействовать с записями, которые принадлежат не вам!");
        }
    }
}
